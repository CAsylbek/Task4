package com.task4.task4.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Информация о текстовом документе")
public class TextDocumentDTO {

    @Schema(description = "Текст")
    private String text;
    @Schema(description = "Дата создания документа")
    private Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TextDocumentDTO() {
    }

    public TextDocumentDTO(String text, Date date) {
        this.text = text;
        this.date = date;
    }
}
