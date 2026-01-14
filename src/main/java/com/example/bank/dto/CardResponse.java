package com.example.bank.dto;

import java.time.YearMonth;

import com.example.bank.entity.Card;
import com.example.bank.enums.CardStatus;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de salida que representa la información visible de una tarjeta bancaria.
 *
 * <p>
 * Se construye a partir de la entidad {@link Card} y expone únicamente
 * los datos necesarios para consumo externo (API), evitando fugas del modelo
 * de dominio.
 * </p>
 */
@Schema(
        name = "CardResponse",
        description = "Representación de respuesta de una tarjeta bancaria"
)
public class CardResponse {

    @Schema(
            description = "Número completo de la tarjeta",
            example = "4111111111111111"
    )
    private String cardNumber;

    @Schema(
            description = "Tipo de tarjeta",
            example = "CREDIT"
    )
    private String cardType;

    @Schema(
            description = "Nombre del titular de la tarjeta",
            example = "Juan Pérez"
    )
    private String holderName;

    @Schema(
            description = "Fecha de expiración de la tarjeta (Año-Mes)",
            example = "2028-12"
    )
    private YearMonth expirationDate;

    @Schema(
            description = "Balance disponible en la tarjeta",
            example = "1500.75"
    )
    private double balance;

    @Schema(
            description = "Estado actual de la tarjeta",
            example = "ACTIVE"
    )
    private CardStatus status;

    /**
     * Construye el DTO a partir de la entidad {@link Card}.
     *
     * @param card entidad de dominio de la tarjeta
     */
    public CardResponse(Card card) {
        this.cardNumber = card.getCardNumber();
        this.cardType = card.getCardType();
        this.holderName = card.getHolderName();
        this.expirationDate = card.getExpirationDate();
        this.balance = card.getBalance();
        this.status = card.getStatus();
    }

    /**
     * @return número de la tarjeta
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @return tipo de tarjeta
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @return nombre del titular
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * @return fecha de expiración (YearMonth)
     */
    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    /**
     * @return balance disponible
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @return estado de la tarjeta
     */
    public CardStatus getStatus() {
        return status;
    }
}
