package com.example.bank.dto;

import com.example.bank.entity.Card;
import com.example.bank.enums.CardStatus;

import java.time.YearMonth;

public class CardResponse {

    private String cardNumber;
    private String cardType;
    private String holderName;
    private YearMonth expirationDate;
    private double balance;
    private CardStatus status;

    public CardResponse(Card card) {
        this.cardNumber = card.getCardNumber();
        this.cardType = card.getCardType();
        this.holderName = card.getHolderName();
        this.expirationDate = card.getExpirationDate();
        this.balance = card.getBalance();
        this.status = card.getStatus();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getHolderName() {
        return holderName;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public double getBalance() {
        return balance;
    }

    public CardStatus getStatus() {
        return status;
    }
}
