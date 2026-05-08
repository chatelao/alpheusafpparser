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
    List<Class<?>> classes = new ArrayList<>();
    classes.add(sf.getClass());
    addClassesFromSF(classes, sf);

    JAXBContext jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    QName qName = new QName(sf.getClass().getSimpleName());
    JAXBElement<Object> root = new JAXBElement<>(qName, Object.class, sf);

    jaxbMarshaller.marshal(root, osw);
  }

  public static void writeXML(OutputStream osw, AFPDocument doc) throws JAXBException {
    List<Class<?>> classes = new ArrayList<>();
    classes.add(AFPDocument.class);
    for (Object obj : doc.getStructuredFields()) {
      if (obj instanceof JAXBElement) {
        Class<?> declaredType = ((JAXBElement<?>) obj).getDeclaredType();
        if (!classes.contains(declaredType)) {
          classes.add(declaredType);
        }
        Object value = ((JAXBElement<?>) obj).getValue();
        if (value instanceof StructuredField) {
          addClassesFromSF(classes, (StructuredField) value);
        }
      } else if (!classes.contains(obj.getClass())) {
        classes.add(obj.getClass());
        if (obj instanceof StructuredField) {
          addClassesFromSF(classes, (StructuredField) obj);
        }
      }
    }

    JAXBContext jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    jaxbMarshaller.marshal(doc, osw);
  }

  private static void addClassesFromSF(List<Class<?>> classes, StructuredField sf) {
    if (sf instanceof IHasTriplets) {
      List<Triplet> triplets = ((IHasTriplets) sf).getTriplets();
      if (triplets != null) {
        for (Triplet t : triplets) {
          if (t != null) {
            addClass(classes, t.getClass());
          }
        }
      }
    }
    if (sf instanceof IHasRepeatingGroups) {
      List<?> rgs = ((IHasRepeatingGroups) sf).getRepeatingGroups();
      if (rgs != null) {
        for (Object rg : rgs) {
          if (rg != null) {
            addClassWithTriplets(classes, rg);
          }
        }
      }
    }
    // Handle other SFs that might have lists of objects but don't implement IHasRepeatingGroups
    // We could use reflection here to find all List fields, but for now let's be more specific or find a better way.
    // Let's check some common ones.
    if (sf instanceof com.mgz.afp.modca.MCF_MapCodedFont_Format1) {
       List<?> rgs = ((com.mgz.afp.modca.MCF_MapCodedFont_Format1)sf).getRepeatingGroups();
       if (rgs != null) {
           for (Object rg : rgs) {
               if (rg != null) addClass(classes, rg.getClass());
           }
       }
    }
    if (sf instanceof com.mgz.afp.foca.CFI_CodedFontIndex) {
        List<?> rgs = ((com.mgz.afp.foca.CFI_CodedFontIndex)sf).getCfiRepeatingGroups();
        if (rgs != null) {
            for (Object rg : rgs) {
                if (rg != null) addClass(classes, rg.getClass());
            }
        }
    }
  }

  private static void addClassWithTriplets(List<Class<?>> classes, Object obj) {
    addClass(classes, obj.getClass());
    if (obj instanceof IHasTriplets) {
      List<Triplet> triplets = ((IHasTriplets) obj).getTriplets();
      if (triplets != null) {
        for (Triplet t : triplets) {
          if (t != null) {
            addClass(classes, t.getClass());
          }
        }
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
