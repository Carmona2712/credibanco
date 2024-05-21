package com.nexos.credibanco.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionAnulationRequest {
    private String cardId;
    private Long transactionId;
}
