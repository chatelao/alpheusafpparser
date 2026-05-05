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
package com.mgz.cli;

import com.mgz.afp.base.AFPDocument;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.xml.AFP2XMLWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AFP2XML {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar alpheus-afp-parser-cli.jar <input-afp-file> [output-xml-file]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = (args.length > 1) ? args[1] : null;

        try {
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.err.println("Input file not found: " + inputPath);
                System.exit(1);
            }

            try (InputStream is = new BufferedInputStream(new FileInputStream(inputFile))) {
                AFPParserConfiguration config = new AFPParserConfiguration();
                config.setInputStream(is);
                AFPParser parser = new AFPParser(config);

                AFPDocument doc = new AFPDocument();
                StructuredField sf;
                while ((sf = parser.parseNextSF()) != null) {
                    JAXBElement<StructuredField> element = new JAXBElement<>(
                            new QName(sf.getClass().getSimpleName()),
                            (Class<StructuredField>) sf.getClass(),
                            sf);
                    doc.addStructuredField(element);
                }

                if (outputPath != null) {
                    try (OutputStream os = new FileOutputStream(outputPath)) {
                        AFP2XMLWriter.writeXML(os, doc);
                    }
                    System.out.println("XML export successful: " + outputPath);
                } else {
                    AFP2XMLWriter.writeXML(System.out, doc);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during XML export: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
