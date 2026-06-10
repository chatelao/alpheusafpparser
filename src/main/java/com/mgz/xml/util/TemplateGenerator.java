package com.mgz.xml.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility to generate fragments for XmlTemplateRegistry from XML snippets with placeholders.
 */
public class TemplateGenerator {

  /**
   * Generates fragments for a given XML template string.
   * Placeholders like {{VAL}} are converted into holes.
   *
   * @param template the XML template string
   * @return an array of fragments
   */
  public static String[] generateFragments(String template) {
    List<String> fragments = new ArrayList<>();
    int start = 0;
    int index;
    while ((index = template.indexOf("{{VAL}}", start)) != -1) {
      fragments.add(template.substring(start, index));
      start = index + 7; // Length of "{{VAL}}"
    }
    fragments.add(template.substring(start));
    return fragments.toArray(new String[0]);
  }

  public static void main(String[] args) {
    // Example usage for generating registry code
    String[] triplets = {
        "AQ", "<AttributeQualifier sequenceNumber=\"{{VAL}}\" levelNumber=\"{{VAL}}\"/>",
        "DP", "<DescriptorPosition objectAreaDescriptorID=\"{{VAL}}\"/>",
        "MU", "<MeasurementUnits xUnitBase=\"{{VAL}}\" yUnitBase=\"{{VAL}}\" xUnitsPerUnitbase=\"{{VAL}}\" yUnitsPerUnitbase=\"{{VAL}}\"/>",
        "STO", "<STO_SetTextOrientation xOrientation=\"{{VAL}}\" yOrientation=\"{{VAL}}\"/>",
        "SIA", "<SIA_SetIntercharacterAdjustment adjustment=\"{{VAL}}\" direction=\"{{VAL}}\"/>"
    };

    for (int i = 0; i < triplets.length; i += 2) {
      String mnemonic = triplets[i];
      String template = triplets[i+1];
      String[] fragments = generateFragments(template);
      System.out.print("register(\"" + mnemonic + "\", new String[] {");
      for (int j = 0; j < fragments.length; j++) {
        System.out.print("\"" + fragments[j].replace("\"", "\\\"") + "\"");
        if (j < fragments.length - 1) {
          System.out.print(", ");
        }
      }
      System.out.println("});");
    }
  }
}
