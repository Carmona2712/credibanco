package com.nexos.credibanco.controller;

import com.nexos.credibanco.dto.TransactionAnulationRequest;
import com.nexos.credibanco.dto.TransactionPurchaseRequest;
import com.nexos.credibanco.model.Transaction;
import com.nexos.credibanco.service.ITransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/transaction")
@Log4j2
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<HashMap<String, String>> purchase(@RequestBody TransactionPurchaseRequest transactionPurchaseRequest) {
        log.info("Request purchase: {}", transactionPurchaseRequest);
        return ResponseEntity.ok(transactionService.purchase(transactionPurchaseRequest.getCardId(), transactionPurchaseRequest.getPrice()));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> findById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        if(transaction != null){
            return ResponseEntity.ok(transaction);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/anulation")
    public ResponseEntity<HashMap<String, String>> anulation(@RequestBody TransactionAnulationRequest transactionAnulationRequest) {
        return ResponseEntity.ok(transactionService.anulation(transactionAnulationRequest.getCardId(), transactionAnulationRequest.getTransactionId()));
    }



}
