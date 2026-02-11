package com.example.bank.dto;

import java.time.YearMonth;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que expone toda la información financiera y operativa de una tarjeta.
 */
@Schema(
        name = "CardDetailsResponse",
        description = "Información completa de la tarjeta incluyendo historial de movimientos"
)
public class CardDetailsResponse {

    @Schema(example = "4111111111111111")
    private String cardNumber;

    @Schema(example = "411111")
    private String cardType;

    @Schema(example = "Juan Perez Hernandez")
    private String holderName;

    @Schema(example = "2030-12")
    private YearMonth expirationDate;

    @Schema(example = "850.00")
    private double balance;

    @Schema(example = "ACTIVE")
    private String status;

    private List<TransactionResponse> transactions;

    public CardDetailsResponse(
            String cardNumber,
            String cardType,
            String holderName,
            YearMonth expirationDate,
            double balance,
            String status,
            List<TransactionResponse> transactions) {

        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.status = status;
        this.transactions = transactions;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCardType() { return cardType; }
    public String getHolderName() { return holderName; }
    public YearMonth getExpirationDate() { return expirationDate; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
    public List<TransactionResponse> getTransactions() { return transactions; }
}
