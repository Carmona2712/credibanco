package com.nexos.credibanco.service.impl;

import com.nexos.credibanco.common.constants.CardMessagesException;
import com.nexos.credibanco.common.enums.CardStatus;
import com.nexos.credibanco.exceptions.NotFoundException;
import com.nexos.credibanco.model.Card;
import com.nexos.credibanco.repository.ICardRepository;
import com.nexos.credibanco.service.ICardService;
import com.nexos.credibanco.utils.CardUtilities;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@Log4j2
public class CardServiceImpl implements ICardService {

    @Autowired
    private ICardRepository cardRepository;
    @Autowired
    private CardUtilities cardUtilities;

    @Override
    @Transactional
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public Card findCard(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return card.get();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Card findCardByNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card != null) {
            return card;
        }
        return null;
    }

    @Override
    @Transactional
    public HashMap<String,String> generateCardNumber(String productId) {
        if(productId.length()<6 || productId.length()>6){
            throw new ServiceException(CardMessagesException.CARD_NUMBER_SIX_DIGITS);
        }
        String cardNumberGenerated;
        do {
            cardNumberGenerated = cardUtilities.generateCardNumber(productId);
        } while (this.findCardByNumber(cardNumberGenerated) != null);

        String expireDate = cardUtilities.generateExpireDate();
        Card card = new Card()
                .builder()
                .cardNumber(cardNumberGenerated)
                .expirationDate(expireDate)
                .status(CardStatus.INACTIVE.getStatus())
                .balance(0)
                .build();
        card = cardRepository.save(card);

        HashMap<String,String> map = new HashMap<>();
        map.put("cardNumber", card.getCardNumber());
        map.put("expireDate", card.getExpirationDate());
        map.put("status", card.getStatus());
        map.put("balance", String.valueOf(card.getBalance()));
        return map;
    }

    @Override
    @Transactional
    public HashMap<Object,Object> cardEnroll(String cardNumber) {
        Card card = this.findCardByNumber(cardNumber);
        if (card != null) {
            if (card.getStatus().equals(CardStatus.INACTIVE.getStatus())) {
                HashMap<Object,Object> response = new HashMap<>();
                card.setStatus(CardStatus.ACTIVE.getStatus());
                cardRepository.save(card);
                response.put("cardNumber", card.getCardNumber());
                response.put("status", card.getStatus());
                response.put("expirationDate", card.getExpirationDate());
                response.put("message", CardMessagesException.CARD_ACTIVATED_SUCESSFULLY);
                return response;
            } else if (card.getStatus().equals(CardStatus.ACTIVE.getStatus())) {
                throw new ServiceException(CardMessagesException.CARD_ALREADY_ACTIVATED);
            } else {
                throw new ServiceException(CardMessagesException.CARD_LOCKED);
            }
        }
        throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
    }

    @Override
    @Transactional
    public String cardLock(String cardNumber) {
        Card card = this.findCardByNumber(cardNumber);
        if (card != null) {
            card.setStatus(CardStatus.LOCKED.getStatus());
            cardRepository.save(card);
            return card.getCardNumber();
        }
        throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
    }

    @Override
    @Transactional
    public HashMap<Object, Object> rechargeBalance(String cardNumber, Double amount) {
        Card card = this.findCardByNumber(cardNumber);
        if (card != null) {
            card.setBalance(card.getBalance() + amount);
            card = cardRepository.save(card);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("cardNumber", card.getCardNumber());
            map.put("balance", card.getBalance());
            return map;
        }
        throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public HashMap<Object, Object> balanceQuery(String cardId) {
        Card card = this.findCardByNumber(cardId);
        if (card != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("cardNumber", card.getCardNumber());
            map.put("balance", card.getBalance());
            return map;
        }
        throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
    }


}
