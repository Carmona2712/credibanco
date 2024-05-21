package com.nexos.credibanco.service;

import com.nexos.credibanco.model.Transaction;

import java.util.HashMap;

public interface ITransactionService {

    public Transaction save(Transaction transaction);
    public Transaction findById(Long id);
    public HashMap<String,String> purchase(String cardNumber, double amount);
    public HashMap<String,String> anulation(String cardNumber, Long transactionId);

}
