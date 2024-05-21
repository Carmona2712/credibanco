package com.nexos.credibanco.service.impl;

import com.nexos.credibanco.common.constants.TransactionMessagesException;
import com.nexos.credibanco.exceptions.NotFoundException;
import com.nexos.credibanco.model.Card;
import com.nexos.credibanco.model.Transaction;
import com.nexos.credibanco.repository.ICardRepository;
import com.nexos.credibanco.repository.ITransactionRepository;
import com.nexos.credibanco.utils.CardUtilities;
import com.nexos.credibanco.utils.TransactionUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    ICardRepository cardRepository;

    @Mock
    CardUtilities cardUtilities;

    @Mock
    TransactionUtilities transactionUtilities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        transactionService.save(transaction);
    }

    @Test
    void findById() {
        Optional<Transaction> transaction = Optional.of(new Transaction());
        when(transactionRepository.findById(anyLong())).thenReturn(transaction);
        transactionService.findById(1L);
    }


    @Test
    void purchase_CardNotFound() {
        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            transactionService.purchase("123456", 100.0);
        });
    }

    @Test
    void purchase() {
        // Crear una tarjeta de prueba
        Card card = new Card();
        card.setCardNumber("123456");
        card.setStatus("Active");
        card.setBalance(200.0);

        // Configurar los mocks para devolver la tarjeta de prueba y un nuevo objeto de transacción
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(cardUtilities.isCardExpired(any(Card.class))).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction().builder().id(1L).dateTransaction(LocalDateTime.now()).build());
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        // Llamar al método de prueba
        HashMap<String, String> result = transactionService.purchase("123456", 100.0);

        // Verificar que la tarjeta y la transacción se guardaron correctamente
        assertEquals("123456", result.get("cardId"));
        assertEquals("100.0", result.get("price"));
        assertEquals("100.0", result.get("balance"));
        assertEquals(TransactionMessagesException.TRANSACTION_APPROVED, result.get("message"));
        assertNotNull(result.get("transactionId"));
        assertNotNull(result.get("date"));
    }

    @Test
    void anulation() {
        // Crear una tarjeta y una transacción de prueba
        Card card = new Card();
        card.setCardNumber("123456");
        card.setStatus("Active");
        card.setBalance(200.0);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setType("Approved");
        transaction.setAmount(100.0);

        // Configurar los mocks para devolver la tarjeta y la transacción de prueba
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(transactionUtilities.isTransactionExpired(any(Transaction.class))).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        // Llamar al método de prueba
        HashMap<String, String> result = transactionService.anulation("123456", 1L);

        // Verificar que la tarjeta y la transacción se guardaron correctamente
        assertEquals("123456", result.get("cardId"));
        assertEquals("100.0", result.get("amount"));
        assertEquals(TransactionMessagesException.TRANSACTION_ANNULLED, result.get("message"));
        assertEquals("Annulled", result.get("status"));
        assertNotNull(result.get("transactionId"));
    }


}