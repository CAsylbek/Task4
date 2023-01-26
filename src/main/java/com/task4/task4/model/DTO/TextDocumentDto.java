package com.task4.task4.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@Schema(description = "Информация о текстовом документе")
@XmlRootElement(name = "textDocument")
public class TextDocumentDto {

    @Schema(description = "Текст")
    private String text;
    @Schema(description = "Дата создания документа")
    private Date date;

    public TextDocumentDto() {
        this.date = new Date();
    }

    public TextDocumentDto(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TextDocumentDto{" +
             "text='" + text + '\'' +
             ", date=" + date +
             '}';
    }
}
