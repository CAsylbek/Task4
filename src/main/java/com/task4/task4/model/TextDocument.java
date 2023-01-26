package com.task4.task4.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "#{@environment.getProperty('elasticsearch.index')}")
@Schema(hidden = true)
public class TextDocument {

    @Id
    @Field(name = "_id", type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Keyword)
    private String message;

    @Field(type = FieldType.Date)
    private Date date;

    public TextDocument() {
        date = new Date();
    }

    public TextDocument(String text) {
        this.message = text;
        date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TextDocument{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
