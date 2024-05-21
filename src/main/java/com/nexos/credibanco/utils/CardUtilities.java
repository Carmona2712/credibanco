package com.nexos.credibanco.utils;

import com.nexos.credibanco.model.Card;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class CardUtilities {

    /**
     * Genera un número de tarjeta basado en un productId dado.
     *
     * @param productId El identificador del producto que se utilizará como base para generar el número de tarjeta.
     * @return Un número de tarjeta generado aleatoriamente que comienza con el productId.
     */
    public String generateCardNumber(String productId) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(productId);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public String generateExpireDate() {
        LocalDate now = LocalDate.now();
        LocalDate expireDate = now.plusYears(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return expireDate.format(formatter);
    }

    public boolean isCardExpired(Card card) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        LocalDate expirationDate = LocalDate.parse("01/" + card.getExpirationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return now.isAfter(expirationDate);
    }


}
