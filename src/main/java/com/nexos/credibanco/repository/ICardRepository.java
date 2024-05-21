package com.nexos.credibanco.repository;

import com.nexos.credibanco.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICardRepository extends JpaRepository<Card, Long> {

    @Query("from Card c where c.cardNumber = :cardNumber")
    public Card findByCardNumber(String cardNumber);

}
