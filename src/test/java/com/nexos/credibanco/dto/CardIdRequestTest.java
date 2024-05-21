package com.nexos.credibanco.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardIdRequestTest {
    @Test
    void getCardId() {
        CardIdRequest cardIdRequest = new CardIdRequest("123456");
        assertEquals("123456", cardIdRequest.getCardId());
    }

    @Test
    void setCardId() {
        CardIdRequest cardIdRequest = new CardIdRequest();
        cardIdRequest.setCardId("123456");
        assertEquals("123456", cardIdRequest.getCardId());
    }

    @Test
    void testEquals() {
        CardIdRequest cardIdRequest1 = new CardIdRequest("123456");
        CardIdRequest cardIdRequest2 = new CardIdRequest("123456");
        assertEquals(cardIdRequest1, cardIdRequest2);

        CardIdRequest cardIdRequest3 = new CardIdRequest("654321");
        assertNotEquals(cardIdRequest1, cardIdRequest3);
    }

    @Test
    void testHashCode() {
        CardIdRequest cardIdRequest1 = new CardIdRequest("123456");
        CardIdRequest cardIdRequest2 = new CardIdRequest("123456");
        assertEquals(cardIdRequest1.hashCode(), cardIdRequest2.hashCode());

        CardIdRequest cardIdRequest3 = new CardIdRequest("654321");
        assertNotEquals(cardIdRequest1.hashCode(), cardIdRequest3.hashCode());
    }

    @Test
    void testToString() {
        CardIdRequest cardIdRequest = new CardIdRequest("123456");
        assertEquals("CardIdRequest(cardId=123456)", cardIdRequest.toString());
    }

    @Test
    void builder() {
        CardIdRequest cardIdRequest = CardIdRequest.builder().cardId("123456").build();
        assertEquals("123456", cardIdRequest.getCardId());
    }
}