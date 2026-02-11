package com.example.bank.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que representa un movimiento de la tarjeta.
 */
@Schema(name = "TransactionResponse", description = "Movimiento asociado a la tarjeta")
public class TransactionResponse {

    @Schema(description = "Tipo de movimiento", example = "PURCHASE")
    private String type;

    @Schema(description = "Monto del movimiento", example = "150.00")
    private double amount;

    @Schema(description = "Fecha y hora del movimiento", example = "2026-02-11T14:30:00")
    private LocalDateTime timestamp;

    public TransactionResponse(String type, double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
