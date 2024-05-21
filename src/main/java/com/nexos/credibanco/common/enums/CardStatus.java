package com.nexos.credibanco.common.enums;

public enum CardStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    LOCKED ("Locked");

    private String status;

    CardStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }


}
