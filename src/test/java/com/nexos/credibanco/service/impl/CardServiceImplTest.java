package com.nexos.credibanco.service.impl;

import com.nexos.credibanco.model.Card;
import com.nexos.credibanco.repository.ICardRepository;
import com.nexos.credibanco.utils.CardUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CardServiceImplTest {


    @InjectMocks
    CardServiceImpl cardService;

    @Mock
    ICardRepository cardRepository;

    @Mock
    CardUtilities cardUtilities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Card card = new Card();
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        cardService.save(card);
    }

    @Test
    void findCard() {
        Optional<Card> card = Optional.of(new Card());
        when(cardRepository.findById(anyLong())).thenReturn(card);
        cardService.findCard(1L);
    }

    @Test
    void findCardByNumber() {
        Card card = new Card();
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        cardService.findCardByNumber("123456");
    }

    @Test
    void generateCardNumber() {
        when(cardUtilities.generateCardNumber(anyString())).thenReturn("123456");
        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);
        when(cardRepository.save(any(Card.class))).thenReturn(new Card());
        cardService.generateCardNumber("123456");
    }

    @Test
    void cardEnroll() {
        Card card = new Card();
        card.setStatus("Inactive");
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        cardService.cardEnroll("123456");
    }

    @Test
    void cardLock() {
        Card card = new Card();
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        cardService.cardLock("123456");
    }

    @Test
    void rechargeBalance() {
        Card card = new Card();
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        cardService.rechargeBalance("123456", 100.0);
    }

    @Test
    void balanceQuery() {
        Card card = new Card();
        when(cardRepository.findByCardNumber(anyString())).thenReturn(card);
        cardService.balanceQuery("123456");
    }

}