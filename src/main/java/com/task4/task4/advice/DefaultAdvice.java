package com.task4.task4.advice;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage ioException(IOException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(SAXException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage saxException(SAXException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(ParserConfigurationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage parserConfigurationException(ParserConfigurationException exception) {
        return new ErrorMessage(exception.getMessage());
    }

}
