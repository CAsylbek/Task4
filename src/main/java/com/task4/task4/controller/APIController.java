package com.task4.task4.controller;

import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.service.TextDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "API текст", description = "Методы для работы с текстовыми документами")
public class APIController {

    final private TextDocumentService textDocumentService;

    public APIController(TextDocumentService textDocumentService) {
        this.textDocumentService = textDocumentService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать текстовый документ")
    public TextDocumentDto postDocuments(@RequestBody TextDocumentDto textDocumentDTO) {
        return textDocumentService.save(textDocumentDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить текстовый документ")
    public List<TextDocumentDto> getDocuments() {
        return textDocumentService.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить текстовый документ")
    public TextDocumentDto deleteDocuments(@PathVariable String id) {
        return textDocumentService.deleteById(id);
    }

    @DeleteMapping("/all")
    @Operation(hidden = true)
    public void deleteAll() {
        textDocumentService.deleteAll();
    }

    @PostMapping(value = "/xml",
         produces = MediaType.APPLICATION_XML_VALUE,
         consumes = MediaType.APPLICATION_XML_VALUE)
    public List<TextDocumentDto> findXmlDocuments(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return textDocumentService.findByMessageXml(xml);
    }
}
