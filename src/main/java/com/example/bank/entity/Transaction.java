package com.example.bank.entity;

import java.time.LocalDateTime;
import com.example.bank.enums.TransactionType;

/**
 * Representa un movimiento financiero asociado a una tarjeta.
 */
public class Transaction {

    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
