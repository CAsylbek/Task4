package com.task4.task4.controller;

import com.task4.task4.model.TextDocument;
import com.task4.task4.service.TextDocumentServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/documents")
public class APIController {

    final private TextDocumentServiceImpl textDocumentService;

    public APIController(TextDocumentServiceImpl textDocumentService) {
        this.textDocumentService = textDocumentService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextDocument> postDocuments(@RequestBody TextDocument textDocument) {
        textDocumentService.save(textDocument);
        return new ResponseEntity(textDocument, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TextDocument>> getDocuments() {
        List<TextDocument> textDocuments = StreamSupport.stream(textDocumentService.findAll().spliterator(), false).toList();
        return new ResponseEntity(textDocuments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocuments(@PathVariable String id) {
        textDocumentService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAll() {
        textDocumentService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
