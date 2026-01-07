package com.example.bank.entity;

import java.time.YearMonth;

import com.example.bank.enums.CardStatus;

public class Card {

    private String cardNumber;       // 16 dígitos
    private String cardType;         // primeros 6 dígitos
    private String holderName;
    private YearMonth expirationDate;
    private double balance;
    private CardStatus status;

    public Card(String cardNumber, String holderName, YearMonth expirationDate) {
        this.cardNumber = cardNumber;
        this.cardType = cardNumber.substring(0, 6);
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        this.balance = 0.0;
        this.status = CardStatus.ACTIVE;
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

    public void toggleStatus() {
        this.status = (this.status == CardStatus.ACTIVE)
                ? CardStatus.BLOCKED
                : CardStatus.ACTIVE;
    }
}
