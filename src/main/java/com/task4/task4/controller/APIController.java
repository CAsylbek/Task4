package com.task4.task4.controller;

import com.task4.task4.model.DTO.TextDocumentDTO;
import com.task4.task4.model.TextDocument;
import com.task4.task4.model.converterToDTO.TextDocumentConvertor;
import com.task4.task4.service.TextDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "API текст", description = "Методы для работы с текстовыми документами")
public class APIController {

    final private TextDocumentService textDocumentService;
    final private TextDocumentConvertor textDocumentConvertor;

    public APIController(TextDocumentService textDocumentService,
                         TextDocumentConvertor textDocumentConvertor) {
        this.textDocumentService = textDocumentService;
        this.textDocumentConvertor = textDocumentConvertor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать текстовый документ")
    public TextDocumentDTO postDocuments(@RequestBody TextDocument textDocument) {
        return textDocumentConvertor.convertToDTO(textDocumentService.save(textDocument));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить текстовый документ")
    public List<TextDocumentDTO> getDocuments() {
        return StreamSupport.stream(textDocumentService.findAll().spliterator(), false)
             .map(textDocumentConvertor::convertToDTO).toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить текстовый документ")
    public void deleteDocuments(@PathVariable String id) {
        // TODO Сделать возврат TextDocumentDTO
        textDocumentService.deleteById(id);
    }

    @DeleteMapping("/all")
    @Operation(hidden = true)
    public void deleteAll() {
        textDocumentService.deleteAll();
    }
}
