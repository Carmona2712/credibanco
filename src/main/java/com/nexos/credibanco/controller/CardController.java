package com.nexos.credibanco.controller;

import com.nexos.credibanco.common.constants.CardMessagesException;
import com.nexos.credibanco.dto.CardIdRequest;
import com.nexos.credibanco.dto.RechargeBalanceRequest;
import com.nexos.credibanco.exceptions.NotFoundException;
import com.nexos.credibanco.service.ICardService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/card")
@Log4j2
public class CardController {

    @Autowired
    private ICardService cardService;

    @GetMapping("/{productId}/number")
    public ResponseEntity<?> generateCardNumber(@PathVariable("productId") String productId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.generateCardNumber(productId));
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollCard(@Valid @RequestBody(required = true) CardIdRequest cardId) {
        HashMap<Object,Object> response = cardService.cardEnroll(cardId.getCardId());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> lockCard(@PathVariable("cardId") String cardId) {
        String response = cardService.cardLock(cardId);
        HashMap<Object,Object> map = new HashMap<>();
        map.put("cardId",response);
        map.put("message",CardMessagesException.CARD_LOCKED);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/balance")
    public ResponseEntity<?> rechargeBalance(@RequestBody RechargeBalanceRequest rechargeBalanceRequest) {
        String cardNumber = rechargeBalanceRequest.getCardId();
        Double amount = rechargeBalanceRequest.getBalance();
        if(cardNumber == null || amount == null){
            return ResponseEntity.badRequest().body(CardMessagesException.CARD_NUMBER_AND_AMOUNT_REQUIRED);
        }
        HashMap<Object, Object> response = cardService.rechargeBalance(cardNumber, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<?> balanceQuery(@PathVariable("cardId") String cardId) {
        HashMap<Object, Object> response = cardService.balanceQuery(cardId);
        return ResponseEntity.ok(response);
    }


}
