package com.task4.task4.model.converterToDTO;

import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.model.TextDocument;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TextDocumentConvertor {

    private ModelMapper modelMapper;

    public TextDocumentConvertor() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(TextDocument.class, TextDocumentDto.class)
             .addMapping(TextDocument::getMessage, TextDocumentDto::setText);
        modelMapper.createTypeMap(TextDocumentDto.class, TextDocument.class)
             .addMapping(TextDocumentDto::getText, TextDocument::setMessage);
    }

    public TextDocumentDto toDTO(TextDocument entity) {
        return modelMapper.map(entity, TextDocumentDto.class);
    }

    public TextDocument toEntity(TextDocumentDto dto) {
        return modelMapper.map(dto, TextDocument.class);
    }
}
