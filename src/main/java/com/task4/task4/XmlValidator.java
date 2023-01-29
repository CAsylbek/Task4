package com.task4.task4;

import com.task4.task4.model.DTO.TextDocumentDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlValidator {

    private String xsdPath;
    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;


    public XmlValidator(String xsdPath) throws JAXBException {
        this.xsdPath = xsdPath;
        jaxbContext = JAXBContext.newInstance(TextDocumentDto.class);
        marshaller = jaxbContext.createMarshaller();
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    private Validator initValidator() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(xsdPath));
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    public boolean isValid(String xml) throws IOException, SAXException {
        Validator validator = initValidator();
        try {
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (SAXException e) {
            return false;
        }
    }

}
