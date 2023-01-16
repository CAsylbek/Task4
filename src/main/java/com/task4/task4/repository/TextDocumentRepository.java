package com.task4.task4.repository;

import com.task4.task4.model.TextDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextDocumentRepository extends ElasticsearchRepository<TextDocument, String>, CrudRepository<TextDocument, String> {
    Iterable<TextDocument> findByMessage(String message);

    Page<TextDocument> findByMessage(String message, String fieldSort, Pageable pageable);

    Page<TextDocument> findAll(Pageable pageable);
}
