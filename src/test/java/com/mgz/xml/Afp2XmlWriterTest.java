package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MSU_MapSuppression;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;

public class Afp2XmlWriterTest {

    @Test
    public void testWriteXMLWithMCF() throws JAXBException {
        MCF_MapCodedFont_Format2 mcf = new MCF_MapCodedFont_Format2();
        mcf.addRepeatingGroup(new MCF_MapCodedFont_Format2.MCF_RepeatingGroup());
        Afp2XmlWriter.writeXml(new ByteArrayOutputStream(), mcf, new AFPParserConfiguration());
    }

    @Test
    public void testWriteXMLWithMSU() throws JAXBException {
        MSU_MapSuppression msu = new MSU_MapSuppression();
        msu.addRepeatingGroup(new MSU_MapSuppression.MSU_RepeatingGroup());
        Afp2XmlWriter.writeXml(new ByteArrayOutputStream(), msu, new AFPParserConfiguration());
    }

    @Test
    public void testWriteXMLWithMMO() throws JAXBException {
        MMO_MapMediumOverlay mmo = new MMO_MapMediumOverlay();
        mmo.addRepeatingGroup(new MMO_MapMediumOverlay.MMO_RepeatingGroup());
        Afp2XmlWriter.writeXml(new ByteArrayOutputStream(), mmo, new AFPParserConfiguration());
    }

    @Test
    public void testWriteXMLWithTriplets() throws JAXBException {
        BDT_BeginDocument bdt = new BDT_BeginDocument();
        bdt.addTriplet(new Triplet.Comment());
        Afp2XmlWriter.writeXml(new ByteArrayOutputStream(), bdt, new AFPParserConfiguration());
    }
}
