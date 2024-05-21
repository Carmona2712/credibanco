package com.nexos.credibanco.controller.advice;

import com.nexos.credibanco.exceptions.NotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CardControllerAdviceTest {

    private CardControllerAdvice cardControllerAdvice = new CardControllerAdvice();

    @Test
    void handleServiceException() {
        ServiceException ex = new ServiceException("Service exception");
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<?> response = cardControllerAdvice.handleServiceException(ex, request);
        assertEquals(409, response.getStatusCodeValue());
        HashMap<String, String> responseBody = (HashMap<String, String>) response.getBody();
        assertEquals("Service exception", responseBody.get("message"));
    }

    @Test
    void handleNotFoundException() {
        NotFoundException ex = new NotFoundException("Not found exception");
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<?> response = cardControllerAdvice.handleServiceException(ex, request);
        assertEquals(404, response.getStatusCodeValue());
        HashMap<String, String> responseBody = (HashMap<String, String>) response.getBody();
        assertEquals("Not found exception", responseBody.get("message"));
    }

    @Test
    void handleHttpClientErrorException() {
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request exception");
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<?> response = cardControllerAdvice.handleHttpClientErrorException(ex, request);
        assertEquals(400, response.getStatusCodeValue());
        HashMap<String, String> responseBody = (HashMap<String, String>) response.getBody();
        assertEquals("400 Bad request exception", responseBody.get("message"));
    }

    @Test
    void messageError() {
        HashMap<String, String> response = CardControllerAdvice.messageError("Error message", HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals("500", response.get("status"));
        assertEquals("Error message", response.get("message"));
    }
}