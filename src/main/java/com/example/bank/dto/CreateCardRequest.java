package com.example.bank.dto;

public class CreateCardRequest {

    private String holderName;
    private int expirationYear;
    private int expirationMonth;

    public String getHolderName() {
        return holderName;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }
}    
