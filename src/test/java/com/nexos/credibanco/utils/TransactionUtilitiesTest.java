package com.nexos.credibanco.utils;

import com.nexos.credibanco.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionUtilitiesTest {

    private TransactionUtilities transactionUtilities = new TransactionUtilities();

    @Test
    void isTransactionExpired_TransactionNotExpired() {
        Transaction transaction = new Transaction();
        transaction.setDateTransaction(LocalDateTime.now());
        assertFalse(transactionUtilities.isTransactionExpired(transaction));
    }

    @Test
    void isTransactionExpired_TransactionExpired() {
        Transaction transaction = new Transaction();
        transaction.setDateTransaction(LocalDateTime.now().minusHours(25));
        assertTrue(transactionUtilities.isTransactionExpired(transaction));
    }
}