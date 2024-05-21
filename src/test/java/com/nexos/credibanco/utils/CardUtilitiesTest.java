package com.nexos.credibanco.utils;

import com.nexos.credibanco.model.Card;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CardUtilitiesTest {
    CardUtilities cardUtilities = new CardUtilities();

    @Test
    void generateCardNumber() {
        String productId = "123";
        String cardNumber = cardUtilities.generateCardNumber(productId);
        assertTrue(cardNumber.startsWith(productId));
        assertEquals(13, cardNumber.length());
    }

    @Test
    void generateExpireDate() {
        String expireDate = cardUtilities.generateExpireDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        LocalDate expectedExpireDate = LocalDate.now().plusYears(3);
        assertEquals(expectedExpireDate.format(formatter), expireDate);
    }

    @Test
    void isCardExpired_CardNotExpired() {
        Card card = new Card();
        card.setExpirationDate(cardUtilities.generateExpireDate());
        assertFalse(cardUtilities.isCardExpired(card));
    }

    @Test
    void isCardExpired_CardExpired() {
        Card card = new Card();
        card.setExpirationDate(LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("MM/yyyy")));
        assertTrue(cardUtilities.isCardExpired(card));
    }
}