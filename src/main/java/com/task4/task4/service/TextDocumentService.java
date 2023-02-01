package com.task4.task4.service;

import com.task4.task4.XmlValidator;
import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.model.TextDocument;
import com.task4.task4.model.converterToDTO.TextDocumentConvertor;
import com.task4.task4.repository.TextDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class TextDocumentService {

    final private TextDocumentRepository textDocumentRepository;
    final private TextDocumentConvertor convertor;

    public TextDocumentService(TextDocumentRepository textDocumentRepository,
                               TextDocumentConvertor convertor) {
        this.textDocumentRepository = textDocumentRepository;
        this.convertor = convertor;
    }

    public TextDocumentDto save(TextDocumentDto textDocumentDTO) {
        TextDocument textDocument = convertor.toEntity(textDocumentDTO);
        return convertor.toDTO(textDocumentRepository.save(textDocument));
    }

    public Page<TextDocument> findByMessage(String message, Pageable pageable) {
        return new PageImpl<>(textDocumentRepository.findByMessageContaining(message, pageable));
    }


    public List<TextDocumentDto> findByMessageXml(String xml) throws IOException, SAXException, ParserConfigurationException {
        XmlValidator xmlValidator = new XmlValidator("src/main/resources/static/Schema.xsd");
        xmlValidator.isValid(xml);

        String message = getByName(xml, "text");
        List<TextDocument> textDocuments = textDocumentRepository.findByMessageContaining(message, null);
        List<TextDocumentDto> documentsDto = textDocuments.stream().map(n -> convertor.toDTO(n)).toList();
        return documentsDto;
    }

    public List<TextDocumentDto> findAll() {
        List<TextDocumentDto> documentsDTOS = new ArrayList<>();
        textDocumentRepository.findAll().forEach(t -> documentsDTOS.add(convertor.toDTO(t)));
        return documentsDTOS;
    }

    public Page<TextDocument> findAll(Pageable pageable) {
        return textDocumentRepository.findAll(pageable);
    }

    public TextDocumentDto deleteById(String id) {
        TextDocumentDto dto = convertor.toDTO(textDocumentRepository.findById(id).orElse(null));
        textDocumentRepository.deleteById(id);
        return dto;
    }

    public void deleteAll() {
        textDocumentRepository.deleteAll();
    }

    private String getByName(String xml, String elName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        doc.getDocumentElement().normalize();

        Element element = doc.getDocumentElement();
        Node node = element.getElementsByTagName(elName).item(0);
        return node.getTextContent();
    }

}
