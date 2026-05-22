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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class Afp2XmlWriter {

  private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
  private static final XPathFactory XPF = XPathFactory.newInstance();
  private static final TransformerFactory TF = TransformerFactory.newInstance();

  private static final Map<List<Class<?>>, JAXBContext> JAXB_CONTEXT_CACHE = new ConcurrentHashMap<>();
  private static final Map<JAXBContext, ConcurrentLinkedQueue<Marshaller>> MARSHALLER_POOL =
      new ConcurrentHashMap<>();

  public static JAXBContext getCachedJaxbContext(List<Class<?>> classes) throws JAXBException {
    if (classes.size() == 1) {
      return JAXB_CONTEXT_CACHE.computeIfAbsent(Collections.singletonList(classes.getFirst()), cl -> {
        try {
          return JAXBContext.newInstance(cl.toArray(new Class[0]));
        } catch (JAXBException e) {
          throw new RuntimeException(e);
        }
      });
    }
    var sortedClasses = new ArrayList<>(classes);
    sortedClasses.sort(Comparator.comparing(Class::getName));
    return JAXB_CONTEXT_CACHE.computeIfAbsent(Collections.unmodifiableList(sortedClasses), cl -> {
      try {
        return JAXBContext.newInstance(cl.toArray(new Class[0]));
      } catch (JAXBException e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   * Acquires a Marshaller for the given JAXBContext from the pool, or creates a new one.
   *
   * @param context the JAXBContext
   * @return a Marshaller
   * @throws JAXBException if creation fails
   */
  public static Marshaller acquireMarshaller(JAXBContext context) throws JAXBException {
    var pool = MARSHALLER_POOL.computeIfAbsent(context, c -> new ConcurrentLinkedQueue<>());
    var marshaller = pool.poll();
    if (marshaller == null) {
      marshaller = context.createMarshaller();
    }
    return marshaller;
  }

  /**
   * Releases a Marshaller back to the pool.
   *
   * @param context    the JAXBContext
   * @param marshaller the Marshaller to release
   */
  public static void releaseMarshaller(JAXBContext context, Marshaller marshaller) {
    var pool = MARSHALLER_POOL.get(context);
    if (pool != null) {
      pool.offer(marshaller);
    }
  }

  public static void writeXML(OutputStream osw, StructuredField sf, AFPParserConfiguration conf) throws JAXBException {
    var classes = new ArrayList<Class<?>>();
    classes.add(sf.getClass());
    addClassesFromSF(classes, sf);

    var jaxbContext = getCachedJaxbContext(classes);
    var jaxbMarshaller = jaxbContext.createMarshaller();
    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    var qualifiedName = new QName(sf.getClass().getSimpleName());
    var root = new JAXBElement<>(qualifiedName, Object.class, sf);

    jaxbMarshaller.marshal(root, osw);
  }

  public static void writeXML(OutputStream osw, AFPDocument doc) throws JAXBException {
    var classes = getRequiredClasses(doc);

    var jaxbContext = getCachedJaxbContext(classes);
    var jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    jaxbMarshaller.marshal(doc, osw);
  }

  public static void writeXML(OutputStream osw, AFPDocument doc, String xpathExpression) throws Exception {
    if (xpathExpression == null || xpathExpression.isBlank()) {
      writeXML(osw, doc);
      return;
    }

    var classes = getRequiredClasses(doc);
    var jaxbContext = getCachedJaxbContext(classes);
    var jaxbMarshaller = jaxbContext.createMarshaller();

    var db = DBF.newDocumentBuilder();
    var domDoc = db.newDocument();

    jaxbMarshaller.marshal(doc, domDoc);

    var xpath = XPF.newXPath();
    var nodes = (org.w3c.dom.NodeList) xpath.evaluate(xpathExpression, domDoc, XPathConstants.NODESET);

    var transformer = TF.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    for (int i = 0; i < nodes.getLength(); i++) {
      var node = nodes.item(i);
      transformer.transform(new DOMSource(node), new StreamResult(osw));
      osw.write('\n');
    }
  }

  private static List<Class<?>> getRequiredClasses(AFPDocument doc) {
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
    return classes;
  }

  public static void addClassesFromSF(List<Class<?>> classes, StructuredField sf) {
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

  public static void addClass(List<Class<?>> classes, Class<?> clazz) {
    if (!classes.contains(clazz)) {
      classes.add(clazz);
      // Check if it's an inner class and add it as well
      // Actually JAXB needs the class itself, but if it's a non-static inner class it might have issues.
      // Most of our repeating groups are static inner classes, which should be fine.
    }
  }
}
