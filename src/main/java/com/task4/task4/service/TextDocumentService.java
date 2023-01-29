package com.task4.task4.service;

import com.task4.task4.XmlValidator;
import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.model.TextDocument;
import com.task4.task4.model.converterToDTO.TextDocumentConvertor;
import com.task4.task4.repository.TextDocumentRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

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


    public List<TextDocumentDto> findByMessageXml(String xml) throws JAXBException, IOException, SAXException {
        List<TextDocumentDto> documentsDto = new ArrayList<>();

        XmlValidator xmlValidator = new XmlValidator("src/main/resources/static/Schema.xsd");

        if (xmlValidator.isValid(xml)) {
            String message = xml.split("<text>|</text>")[1];
            List<TextDocument> textDocuments = textDocumentRepository.findByMessageContaining(message, null);
            textDocuments.stream().forEachOrdered(n -> documentsDto.add(convertor.toDTO(n)));
        } else {
            return null;
        }

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
}
