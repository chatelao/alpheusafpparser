/*
Copyright 2015 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.xml;

import com.mgz.afp.base.AFPDocument;
import com.mgz.afp.base.IHasRepeatingGroups;
import com.mgz.afp.base.IHasTriplets;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AFP2XMLWriter {

  public static void writeXML(OutputStream osw, StructuredField sf, AFPParserConfiguration conf) throws JAXBException {
    var classes = new ArrayList<Class<?>>();
    classes.add(sf.getClass());
    addClassesFromSF(classes, sf);

    var jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
    var jaxbMarshaller = jaxbContext.createMarshaller();
    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    var qName = new QName(sf.getClass().getSimpleName());
    var root = new JAXBElement<>(qName, Object.class, sf);

    jaxbMarshaller.marshal(root, osw);
  }

  public static void writeXML(OutputStream osw, AFPDocument doc) throws JAXBException {
    var classes = new ArrayList<Class<?>>();
    classes.add(AFPDocument.class);
    doc.getStructuredFields().forEach(obj -> {
      if (obj instanceof JAXBElement<?> jaxbElement) {
        var declaredType = jaxbElement.getDeclaredType();
        if (!classes.contains(declaredType)) {
          classes.add(declaredType);
        }
        var value = jaxbElement.getValue();
        if (value instanceof StructuredField sf) {
          addClassesFromSF(classes, sf);
        }
      } else if (!classes.contains(obj.getClass())) {
        classes.add(obj.getClass());
        if (obj instanceof StructuredField sf) {
          addClassesFromSF(classes, sf);
        }
      }
    });

    var jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
    var jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    jaxbMarshaller.marshal(doc, osw);
  }

  private static void addClassesFromSF(List<Class<?>> classes, StructuredField sf) {
    if (sf instanceof IHasTriplets iHasTriplets) {
      var triplets = iHasTriplets.getTriplets();
      if (triplets != null) {
        triplets.stream().filter(java.util.Objects::nonNull).forEach(t -> addClass(classes, t.getClass()));
      }
    }
    if (sf instanceof IHasRepeatingGroups iHasRepeatingGroups) {
      var rgs = iHasRepeatingGroups.getRepeatingGroups();
      if (rgs != null) {
        rgs.stream().filter(java.util.Objects::nonNull).forEach(rg -> addClassWithTriplets(classes, rg));
      }
    }
    // Handle other SFs that might have lists of objects but don't implement IHasRepeatingGroups
    // We could use reflection here to find all List fields, but for now let's be more specific or find a better way.
    // Let's check some common ones.
    if (sf instanceof com.mgz.afp.modca.MCF_MapCodedFont_Format1 mcf1) {
      var rgs = mcf1.getRepeatingGroups();
      if (rgs != null) {
        rgs.stream().filter(java.util.Objects::nonNull).forEach(rg -> addClass(classes, rg.getClass()));
      }
    }
    if (sf instanceof com.mgz.afp.foca.CFI_CodedFontIndex cfi) {
      var rgs = cfi.getCfiRepeatingGroups();
      if (rgs != null) {
        rgs.stream().filter(java.util.Objects::nonNull).forEach(rg -> addClass(classes, rg.getClass()));
      }
    }
  }

  private static void addClassWithTriplets(List<Class<?>> classes, Object obj) {
    addClass(classes, obj.getClass());
    if (obj instanceof IHasTriplets iHasTriplets) {
      var triplets = iHasTriplets.getTriplets();
      if (triplets != null) {
        triplets.stream().filter(java.util.Objects::nonNull).forEach(t -> addClass(classes, t.getClass()));
      }
    }
  }

  private static void addClass(List<Class<?>> classes, Class<?> clazz) {
    if (!classes.contains(clazz)) {
      classes.add(clazz);
      // Check if it's an inner class and add it as well
      // Actually JAXB needs the class itself, but if it's a non-static inner class it might have issues.
      // Most of our repeating groups are static inner classes, which should be fine.
    }
  }
}
