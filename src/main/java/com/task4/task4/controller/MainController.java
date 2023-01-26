package com.task4.task4.controller;

import com.task4.task4.model.TextDocument;
import com.task4.task4.model.converterToDTO.TextDocumentConvertor;
import com.task4.task4.service.TextDocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
public class MainController {

    final private TextDocumentService textDocumentService;
    final private TextDocumentConvertor convertor;

    @Value("${page.size}")
    private int pageSize;

    public MainController(TextDocumentService textDocumentService,
                          TextDocumentConvertor convertor) {
        this.textDocumentService = textDocumentService;
        this.convertor = convertor;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/documents/create")
    public String createPage(Model model) {
        model.addAttribute("textDocument", new TextDocument());
        return "createDoc";
    }

    @PostMapping("/documents/create")
    public String createDocument(Model model, @ModelAttribute("textDocument") TextDocument textDocument) {
            textDocumentService.save(convertor.toDTO(textDocument));
        return "createDoc";
    }

    @GetMapping("/documents/find")
    public String findDocuments(Model model,
                                @RequestParam(name = "message", defaultValue = "") String message,
                                @RequestParam(name = "page", required = false, defaultValue = "0") String page,
                                @RequestParam(name = "sort", required = false, defaultValue = "date") String sortProperty) {

        Pageable pageable = PageRequest.of(Integer.parseInt(page), pageSize, Sort.by(Sort.Direction.ASC, sortProperty));
        Page<TextDocument> pageDocuments = textDocumentService.findByMessage(message, pageable);

        model.addAttribute("pageDocuments", pageDocuments);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1, pageDocuments.getTotalPages()).toArray());
        model.addAttribute("message", message);
        model.addAttribute("sort", sortProperty);
        return "findDoc";
    }

    @GetMapping("/documents/all")
    public String getAllDocuments(Model model,
                                  @RequestParam(name = "page", required = false, defaultValue = "0") String page,
                                  @RequestParam(name = "sort", required = false, defaultValue = "date") String sortProperty) {

        Pageable pageable = PageRequest.of(Integer.parseInt(page), pageSize, Sort.by(Sort.Direction.ASC, sortProperty));
        Page<TextDocument> pageDocuments = textDocumentService.findAll(pageable);

        model.addAttribute("pageDocuments", pageDocuments);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1, pageDocuments.getTotalPages()).toArray());
        model.addAttribute("sort", sortProperty);
        return "docs";
    }

}
