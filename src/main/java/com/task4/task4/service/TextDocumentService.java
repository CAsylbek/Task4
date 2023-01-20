package com.task4.task4.service;

import com.task4.task4.model.TextDocument;
import com.task4.task4.repository.TextDocumentRepository;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
public class TextDocumentService {

    final private TextDocumentRepository textDocumentRepository;
    final private ElasticsearchRestTemplate elasticsearchTemplate;

    public TextDocumentService(TextDocumentRepository textDocumentRepository,
                               ElasticsearchRestTemplate elasticsearchTemplate) {
        this.textDocumentRepository = textDocumentRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public TextDocument save(TextDocument textDocument) {
        return textDocumentRepository.save(textDocument);
    }

    public Iterable<TextDocument> findByMessage(String message) {
        return textDocumentRepository.findByMessage(message);
    }

    public Page<TextDocument> findByMessage(String message, String fieldSort, Pageable pageable) {
        Query query = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("message", ".*" + message + "*."))
                .withSort(SortBuilders.fieldSort(fieldSort))
                .build();
        SearchHits<TextDocument> searchHits = elasticsearchTemplate.search(query, TextDocument.class, IndexCoordinates.of("textdoc"));

        List<TextDocument> textDocumentList = new ArrayList<>();
        searchHits.forEach(n -> textDocumentList.add(n.getContent()));

        PagedListHolder page = new PagedListHolder(textDocumentList);
        page.setPageSize(pageable.getPageSize());
        page.setPage(pageable.getPageNumber());
        SortDefinition sortDefinition = new MutableSortDefinition("message", true, true);
        page.setSort(sortDefinition);

        return new PageImpl<>(page.getPageList(), pageable, textDocumentList.size());
    }

    public Iterable<TextDocument> findAll() {
        return textDocumentRepository.findAll();
    }

    public Iterable<TextDocument> findAll(Sort sort) {
        return textDocumentRepository.findAll(sort);
    }


    public Page<TextDocument> findAll(Pageable pageable) {
        return textDocumentRepository.findAll(pageable);
    }

    public void deleteById(String id) {
        textDocumentRepository.deleteById(id);
    }

    public void deleteAll() {
        textDocumentRepository.deleteAll();
    }
}
