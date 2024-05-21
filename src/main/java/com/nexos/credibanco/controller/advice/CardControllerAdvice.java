package com.nexos.credibanco.controller.advice;

import com.nexos.credibanco.controller.CardController;
import com.nexos.credibanco.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;

@RestControllerAdvice(assignableTypes = CardController.class)
@Log4j2
public class CardControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleServiceException(ServiceException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageError(ex.getMessage(),HttpStatus.CONFLICT));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleServiceException(NotFoundException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError(ex.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError(ex.getMessage(),HttpStatus.BAD_REQUEST));
    }

    public static HashMap<String, String> messageError(String message,HttpStatus httpStatus){
        HashMap<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(httpStatus.value()));
        map.put("message", message);
        map.put("date", new Date().toString());
        return map;
    }

}