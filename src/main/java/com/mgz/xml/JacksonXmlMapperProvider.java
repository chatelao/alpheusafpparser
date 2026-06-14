/*
Copyright 2024 Rudolf Fiala

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

import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides a thread-safe, singleton {@link XmlMapper} configured for AFP to XML conversion.
 */
public class JacksonXmlMapperProvider {

  private static final WstxInputFactory INPUT_FACTORY = new WstxInputFactory();
  private static final WstxOutputFactory OUTPUT_FACTORY = new WstxOutputFactory();

  private static final XmlMapper XML_MAPPER;
  private static final XmlMapper FRAGMENT_MAPPER;

  private static final ConcurrentHashMap<Class<?>, ObjectWriter> WRITER_CACHE = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<Class<?>, ObjectWriter> FRAGMENT_WRITER_CACHE = new ConcurrentHashMap<>();

  private static final ConcurrentHashMap<Class<?>, ObjectWriter> INDENT_WRITER_CACHE = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<Class<?>, ObjectWriter> FRAGMENT_INDENT_WRITER_CACHE = new ConcurrentHashMap<>();

  static {
    // Note: .configureForSpeed() is NOT used here to allow for more granular tuning
    // of performance-related properties.
    OUTPUT_FACTORY.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);
    try {
      OUTPUT_FACTORY.setProperty("com.ctc.wstx.addSpaceAfterEmptyElem", false);
      OUTPUT_FACTORY.setProperty("com.ctc.wstx.useDoubleQuotesInXmlDecl", true);
      OUTPUT_FACTORY.setProperty("com.ctc.wstx.outputBufferSize", 65536);
      OUTPUT_FACTORY.setProperty("org.codehaus.stax2.validation.checkStructure", false);
    } catch (Exception e) {
      // Ignore
    }

    XML_MAPPER = XmlMapper.builder(new XmlFactory(INPUT_FACTORY, OUTPUT_FACTORY))
        .nameForTextElement("text")
        .enable(StreamWriteFeature.USE_FAST_DOUBLE_WRITER)
        .addModule(new BlackbirdModule())
        .build();
    // Disable indentation for better performance in high-throughput environments
    XML_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
    XML_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    // Do not serialize empty or null fields, similar to JAXB default behavior in many cases
    XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    FRAGMENT_MAPPER = XML_MAPPER.copy();
    FRAGMENT_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
    // Avoid "Trying to output second root" in fragment mode
    FRAGMENT_MAPPER.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
  }

  /**
   * Returns the singleton {@link XmlMapper} instance.
   *
   * @return the XmlMapper
   */
  public static XmlMapper getMapper() {
    return XML_MAPPER;
  }

  /**
   * Returns the singleton {@link XmlMapper} instance configured for fragment writing.
   *
   * @return the fragment XmlMapper
   */
  public static XmlMapper getFragmentMapper() {
    return FRAGMENT_MAPPER;
  }

  /**
   * Returns a pre-cached {@link ObjectWriter} for the given class and configuration.
   *
   * @param clazz the class to get the writer for
   * @param fragment if true, use fragment configuration
   * @param indent if true, enable indentation
   * @return the cached ObjectWriter
   */
  public static ObjectWriter getCachedWriter(Class<?> clazz, boolean fragment, boolean indent) {
    if (fragment) {
      if (indent) {
        return FRAGMENT_INDENT_WRITER_CACHE.computeIfAbsent(clazz,
            c -> FRAGMENT_MAPPER.writerFor(c).with(SerializationFeature.INDENT_OUTPUT));
      }
      return FRAGMENT_WRITER_CACHE.computeIfAbsent(clazz, FRAGMENT_MAPPER::writerFor);
    } else {
      if (indent) {
        return INDENT_WRITER_CACHE.computeIfAbsent(clazz,
            c -> XML_MAPPER.writerFor(c).with(SerializationFeature.INDENT_OUTPUT));
      }
      return WRITER_CACHE.computeIfAbsent(clazz, XML_MAPPER::writerFor);
    }
  }

  /**
   * Returns the XML output factory.
   *
   * @return the XMLOutputFactory
   */
  public static XMLOutputFactory getOutputFactory() {
    return OUTPUT_FACTORY;
  }

  /**
   * Returns the XML input factory.
   *
   * @return the XMLInputFactory
   */
  public static XMLInputFactory getInputFactory() {
    return INPUT_FACTORY;
  }
}
