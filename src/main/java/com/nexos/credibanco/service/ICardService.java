package com.nexos.credibanco.service;

import com.nexos.credibanco.model.Card;

import java.util.HashMap;

public interface ICardService {

    public Card save(Card card);
    public Card findCard(Long id);
    public Card findCardByNumber(String cardNumber);

    public HashMap<String,String> generateCardNumber(String productId);
    public HashMap<Object,Object> cardEnroll(String cardNumber);
    public String cardLock(String cardNumber);
    public HashMap<Object,Object> rechargeBalance(String cardNumber, Double amount);
    public HashMap<Object,Object> balanceQuery(String cardId);

}
