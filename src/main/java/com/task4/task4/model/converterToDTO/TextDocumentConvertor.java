package com.task4.task4.model.converterToDTO;

import com.task4.task4.model.DTO.TextDocumentDTO;
import com.task4.task4.model.TextDocument;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TextDocumentConvertor {

    final private ModelMapper modelMapper;

    public TextDocumentConvertor() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(TextDocument.class, TextDocumentDTO.class)
             .addMapping(TextDocument::getMessage, TextDocumentDTO::setText);
    }

    public TextDocumentDTO convertToDTO(TextDocument entity) {
        return modelMapper.map(entity, TextDocumentDTO.class);
    }

}
