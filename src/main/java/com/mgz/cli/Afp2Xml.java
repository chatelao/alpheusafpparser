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
import com.mgz.xml.Afp2XmlWriter;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Afp2Xml {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        var isDirectoryMode = false;
        String inputPath = null;
        String outputPath = null;

        for (var i = 0; i < args.length; i++) {
            if ("-d".equals(args[i]) || "--directory".equals(args[i])) {
                isDirectoryMode = true;
            } else if (inputPath == null) {
                inputPath = args[i];
            } else if (outputPath == null) {
                outputPath = args[i];
            }
        }

        if (inputPath == null) {
            printUsage();
            System.exit(1);
        }

        try {
            var input = new File(inputPath);
            if (!input.exists()) {
                System.err.println("Input not found: " + inputPath);
                System.exit(1);
            }

            if (isDirectoryMode) {
                if (!input.isDirectory()) {
                    System.err.println("Input is not a directory: " + inputPath);
                    System.exit(1);
                }

                var files = input.listFiles((dir, name) -> name.toLowerCase().endsWith(".afp"));
                if (files != null) {
                    for (var f : files) {
                        var outputFile = new File(f.getAbsolutePath() + ".xml");
                        convertToXml(f, outputFile);
                    }
                }
            } else {
                var outputFile = (outputPath != null) ? new File(outputPath) : null;
                convertToXml(input, outputFile);
            }
        } catch (Exception e) {
            System.err.println("Error during XML export: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends StructuredField> JAXBElement<T> createJAXBElement(T sf) {
        return new JAXBElement<>(
                new QName(sf.getClass().getSimpleName()),
                (Class<T>) sf.getClass(),
                sf);
    }

    private static void printUsage() {
        System.err.println("Usage: java -jar alpheus-afp-parser-cli.jar [-d|--directory] <input-afp-file/dir> [output-xml-file]");
        System.err.println("Options:");
        System.err.println("  -d, --directory    Convert all .afp files in the specified directory to XML");
    }

    private static void convertToXml(File inputFile, File outputFile) throws Exception {
        try (var is = new BufferedInputStream(new FileInputStream(inputFile))) {
            var config = new AFPParserConfiguration();
            config.setInputStream(is);
            var parser = new AFPParser(config);

            var doc = new AFPDocument();
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                doc.addStructuredField(createJAXBElement(sf));
            }

            if (outputFile != null) {
                try (var os = new FileOutputStream(outputFile)) {
                    Afp2XmlWriter.writeXML(os, doc);
                }
                System.out.println("XML export successful: " + outputFile.getPath());
            } else {
                Afp2XmlWriter.writeXML(System.out, doc);
            }
        }
    }
}
