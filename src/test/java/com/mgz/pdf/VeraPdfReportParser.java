/*
Copyright 2026 Rudolf Fiala

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

package com.mgz.pdf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for VeraPDF XML preflight reports.
 */
public class VeraPdfReportParser {

    /**
     * Represents the result of a VeraPDF validation.
     */
    public record ValidationResult(boolean isCompliant, String profileName, List<String> failedRules) {
    }

    /**
     * Parses a VeraPDF XML report from an input stream.
     *
     * @param is the input stream containing the XML report
     * @return the validation result
     * @throws Exception if an error occurs during parsing
     */
    public ValidationResult parse(InputStream is) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);

        boolean isCompliant = false;
        String profileName = "Unknown";
        List<String> failedRules = new ArrayList<>();

        // VeraPDF reports can have slightly different structures depending on version/config.
        // We try to find the <validation> or <validationReport> element.

        NodeList validationNodes = doc.getElementsByTagNameNS("*", "validation");
        if (validationNodes.getLength() == 0) {
            validationNodes = doc.getElementsByTagNameNS("*", "validationReport");
        }

        if (validationNodes.getLength() > 0) {
            Element validationEl = (Element) validationNodes.item(0);

            profileName = validationEl.getAttribute("profileName");

            // isCompliant can be in "isCompliance" (old) or inferred from "statement" (new) or "isCompliant" attribute
            String isCompliantAttr = validationEl.getAttribute("isCompliant");
            if (isCompliantAttr.isEmpty()) {
                isCompliantAttr = validationEl.getAttribute("isCompliance");
            }

            if (!isCompliantAttr.isEmpty()) {
                isCompliant = Boolean.parseBoolean(isCompliantAttr);
            } else {
                // Try to infer from statement
                String statement = validationEl.getAttribute("statement");
                if (statement != null && statement.toLowerCase().contains("is compliant")) {
                    isCompliant = true;
                }
            }

            // Extract failed rules
            NodeList ruleNodes = doc.getElementsByTagNameNS("*", "rule");
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element ruleEl = (Element) ruleNodes.item(i);
                String status = ruleEl.getAttribute("status");
                if ("failed".equalsIgnoreCase(status)) {
                    String ruleId = ruleEl.getAttribute("specification");
                    if (ruleId.isEmpty()) {
                        ruleId = ruleEl.getElementsByTagNameNS("*", "description").item(0).getTextContent();
                    }
                    failedRules.add(ruleId);
                }
            }
        }

        return new ValidationResult(isCompliant, profileName, failedRules);
    }
}
