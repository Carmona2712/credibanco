package com.nexos.credibanco.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionPurchaseRequest {
    private String cardId;
    private double price;
}
