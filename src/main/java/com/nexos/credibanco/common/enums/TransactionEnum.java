package com.nexos.credibanco.common.enums;

public enum TransactionEnum {
    APPROVED("Approved"),
    ANNULLED("Annulled");
    private String status;

    TransactionEnum(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
