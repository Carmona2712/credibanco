package com.nexos.credibanco.service.impl;

import com.nexos.credibanco.common.constants.CardMessagesException;
import com.nexos.credibanco.common.constants.TransactionMessagesException;
import com.nexos.credibanco.common.enums.TransactionEnum;
import com.nexos.credibanco.exceptions.NotFoundException;
import com.nexos.credibanco.model.Card;
import com.nexos.credibanco.model.Transaction;
import com.nexos.credibanco.repository.ICardRepository;
import com.nexos.credibanco.repository.ITransactionRepository;
import com.nexos.credibanco.service.ITransactionService;
import com.nexos.credibanco.utils.CardUtilities;
import com.nexos.credibanco.utils.TransactionUtilities;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;
    @Autowired
    private ICardRepository cardRepository;
    @Autowired
    private CardUtilities cardUtilities;
    @Autowired
    private TransactionUtilities transactionUtilities;

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()){
            return transaction.get();
        }
        return null;
    }

    @Override
    public HashMap<String, String> purchase(String cardNumber, double amount) {
        HashMap<String,String> map = new HashMap<>();
        Card card = cardRepository.findByCardNumber(cardNumber);

        if(card == null){
            throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
        }

        if(card.getStatus().equals("Locked")){
            throw new ServiceException(CardMessagesException.CARD_LOCKED);
        }else if(card.getStatus().equals("Inactive")){
            throw new ServiceException(CardMessagesException.CARD_NOT_ACTIVATED);
        }

        if(cardUtilities.isCardExpired(card)){
            throw new ServiceException(CardMessagesException.CARD_EXPIRED);
        }

        if(card.getBalance()<amount){
            throw new ServiceException(CardMessagesException.CARD_NOT_SUFFICIENT_BALANCE);
        }

        card.setBalance(card.getBalance()-amount);
        card = cardRepository.save(card);

        Transaction transaction = new Transaction()
                .builder()
                .amount(amount)
                .dateTransaction(LocalDateTime.now())
                .Card(card)
                .type(TransactionEnum.APPROVED.getStatus()).build();
        transaction = this.save(transaction);
        map.put("transactionId",transaction.getId().toString());
        map.put("cardId",card.getCardNumber());
        map.put("balance",String.valueOf(card.getBalance()));
        map.put("message", TransactionMessagesException.TRANSACTION_APPROVED);
        map.put("price",String.valueOf(amount));
        map.put("date",transaction.getDateTransaction().toString());
        return map;
    }

    @Override
    public HashMap<String, String> anulation(String cardNumber, Long transactionId) {
        HashMap<String,String> map = new HashMap<>();
        Transaction transaction = this.findById(transactionId);
        if(transaction == null){
            throw new NotFoundException(TransactionMessagesException.TRANSACTION_NOT_FOUND);
        }

        if(transaction.getType().equals("Annulled")){
            throw new ServiceException(TransactionMessagesException.TRANSACTION_ALREADY_ANNULLED);
        }

        Card card = cardRepository.findByCardNumber(cardNumber);
        if(card == null){
            throw new NotFoundException(CardMessagesException.CARD_NOT_FOUND);
        }

        if(transactionUtilities.isTransactionExpired(transaction)){
            throw new ServiceException(TransactionMessagesException.TRANSACTION_EXPIRED);
        }

        card.setBalance(card.getBalance()+transaction.getAmount());
        card = cardRepository.save(card);

        transaction.setType(TransactionEnum.ANNULLED.getStatus());
        transaction = this.save(transaction);

        map.put("transactionId",transaction.getId().toString());
        map.put("cardId",card.getCardNumber());
        map.put("message", TransactionMessagesException.TRANSACTION_ANNULLED);
        map.put("amount",String.valueOf(transaction.getAmount()));
        map.put("status",transaction.getType());
        return map;
    }

}
