package com.task4.task4.repository;

import com.task4.task4.model.TextDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextDocumentRepository extends ElasticsearchRepository<TextDocument, String> {

    Page<TextDocument> findAll(Pageable pageable);
    List<TextDocument> findByMessageContaining(String message, Pageable pageable);
}
