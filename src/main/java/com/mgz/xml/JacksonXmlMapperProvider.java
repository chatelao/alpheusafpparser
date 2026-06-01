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

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import java.io.IOException;

/**
 * Provides a thread-safe, singleton {@link XmlMapper} configured for AFP to XML conversion.
 */
public class JacksonXmlMapperProvider {

  private static final XmlMapper XML_MAPPER;
  private static final XmlMapper FRAGMENT_MAPPER;

  private static final XmlMapper WOODSTOX_MAPPER;
  private static final XmlMapper WOODSTOX_FRAGMENT_MAPPER;

  static {
    XML_MAPPER = XmlMapper.builder(new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()))
        .nameForTextElement("text")
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

    WOODSTOX_MAPPER = XmlMapper.builder(new XmlFactory(new WstxInputFactory(), new WstxOutputFactory()))
        .nameForTextElement("text")
        .build();
    WOODSTOX_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
    WOODSTOX_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    WOODSTOX_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    WOODSTOX_FRAGMENT_MAPPER = WOODSTOX_MAPPER.copy();
    WOODSTOX_FRAGMENT_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
    WOODSTOX_FRAGMENT_MAPPER.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
  }

  /**
   * Returns the singleton {@link XmlMapper} instance.
   *
   * @return the XmlMapper
   */
  public static XmlMapper getMapper() {
    return getMapper(false);
  }

  /**
   * Returns the singleton {@link XmlMapper} instance based on the backend choice.
   *
   * @param useWoodstox if true, return Woodstox mapper
   * @return the XmlMapper
   */
  public static XmlMapper getMapper(boolean useWoodstox) {
    return useWoodstox ? WOODSTOX_MAPPER : XML_MAPPER;
  }

  /**
   * Returns the singleton {@link XmlMapper} instance configured for fragment writing.
   *
   * @return the fragment XmlMapper
   */
  public static XmlMapper getFragmentMapper() {
    return getFragmentMapper(false);
  }

  /**
   * Returns the singleton {@link XmlMapper} instance configured for fragment writing based on the backend choice.
   *
   * @param useWoodstox if true, return Woodstox fragment mapper
   * @return the fragment XmlMapper
   */
  public static XmlMapper getFragmentMapper(boolean useWoodstox) {
    return useWoodstox ? WOODSTOX_FRAGMENT_MAPPER : FRAGMENT_MAPPER;
  }
}
