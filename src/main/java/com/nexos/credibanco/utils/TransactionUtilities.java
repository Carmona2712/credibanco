package com.nexos.credibanco.utils;


import com.nexos.credibanco.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Log4j2
public class TransactionUtilities {

    public boolean isTransactionExpired(Transaction transaction) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = transaction.getDateTransaction().plusHours(24);
        log.info("Expiration date: " + expirationDate);
        log.info("Now: " + now);
        log.info("Is transaction expired: " + now.isAfter(expirationDate));
        return now.isAfter(expirationDate);
    }

}
