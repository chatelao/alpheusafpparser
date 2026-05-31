package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class JacksonOutputInspectorTest {
    @Test
    public void inspectBdt() throws Exception {
        BDT_BeginDocument bdt = new BDT_BeginDocument();
        bdt.setName("MYDOC");
        List<Triplet> triplets = new ArrayList<>();
        Triplet.Comment c = new Triplet.Comment();
        c.comment = "Hello";
        triplets.add(c);
        bdt.setTriplets(triplets);

        String xml = JacksonXmlMapperProvider.getMapper().writerWithDefaultPrettyPrinter().withRootName("BDT_BeginDocument").writeValueAsString(bdt);
        System.out.println("Jackson BDT output:");
        System.out.println(xml);
    }
}
