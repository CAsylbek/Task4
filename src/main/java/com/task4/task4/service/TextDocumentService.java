package com.task4.task4.service;

import com.task4.task4.model.TextDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TextDocumentService {

    TextDocument save(TextDocument textDocument);
    void deleteById(String id);
    void deleteAll();
    Page<TextDocument> findByMessage(String message, String fieldSort, Pageable pageable);
    Page<TextDocument> findAll(Pageable pageable);

    Iterable<TextDocument> findAll(Sort sort);
}
