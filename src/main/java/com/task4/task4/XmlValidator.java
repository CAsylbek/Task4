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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

public class XmlValidator {

    private String xmlPath;
    private String xsdPath;
    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;


    public XmlValidator(String xmlPath, String xsdPath) throws JAXBException {
        this();
        this.xmlPath = xmlPath;
        this.xsdPath = xsdPath;
    }

    public XmlValidator() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(TextDocumentDto.class);
        marshaller = jaxbContext.createMarshaller();
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getXsdPath() {
        return xsdPath;
    }

    public void setXsdPath(String xsdPath) {
        this.xsdPath = xsdPath;
    }

    public Validator initValidator() throws IOException, SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(xsdPath));
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    public boolean isValid() throws IOException, SAXException {
        if (!new File(xmlPath).isFile()) {
            throw new FileNotFoundException(xmlPath);
        }

        Validator validator = initValidator();
        try {
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (SAXException e) {
            return false;
        }
    }

    public void createXmlFileFromDto(TextDocumentDto textDocumentDto) throws IOException, JAXBException {
        File file = new File(xmlPath);
        file.createNewFile();
        marshaller.marshal(textDocumentDto, file);
    }

    public TextDocumentDto createDtoFromXmlFile() throws NullPointerException, JAXBException {
        File file = new File(xmlPath);
        return (TextDocumentDto) unmarshaller.unmarshal(file);
    }

    public String writeTextDocumentDtoAsString(TextDocumentDto dto) throws JAXBException {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(dto, stringWriter);
        String xmlString = stringWriter.toString().replaceAll("\n", "")
             .replaceAll("\t", "");
        return xmlString;
    }
}
