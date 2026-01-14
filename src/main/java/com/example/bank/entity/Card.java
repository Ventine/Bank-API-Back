package com.example.bank.entity;

import java.time.YearMonth;

import com.example.bank.enums.CardStatus;

/**
 * Entidad de dominio que representa una tarjeta bancaria.
 *
 * <p>
 * Modela el estado y comportamiento esencial de una tarjeta.
 * No contiene anotaciones de infraestructura ni de API, ya que
 * pertenece al núcleo del dominio.
 * </p>
 */
public class Card {

    /**
     * Número completo de la tarjeta (16 dígitos).
     */
    private String cardNumber;

    /**
     * Tipo de tarjeta derivado de los primeros 6 dígitos (BIN).
     */
    private String cardType;

    /**
     * Nombre del titular de la tarjeta.
     */
    private String holderName;

    /**
     * Fecha de expiración de la tarjeta.
     */
    private YearMonth expirationDate;

    /**
     * Balance actual de la tarjeta.
     */
    private double balance;

    /**
     * Estado actual de la tarjeta.
     */
    private CardStatus status;

    /**
     * Crea una nueva tarjeta con estado inicial activo y balance cero.
     *
     * <p>
     * El tipo de tarjeta se deriva automáticamente a partir
     * del número de tarjeta.
     * </p>
     *
     * @param cardNumber número completo de la tarjeta
     * @param holderName nombre del titular
     * @param expirationDate fecha de expiración
     */
    public Card(String cardNumber, String holderName, YearMonth expirationDate) {
        this.cardNumber = cardNumber;
        this.cardType = cardNumber.substring(0, 6);
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        this.balance = 0.0;
        this.status = CardStatus.ACTIVE;
    }

    /**
     * @return número completo de la tarjeta
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @return tipo de tarjeta (BIN)
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
     * @return fecha de expiración
     */
    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    /**
     * @return balance actual
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @return estado actual de la tarjeta
     */
    public CardStatus getStatus() {
        return status;
    }

    /**
     * Alterna el estado de la tarjeta entre {@code ACTIVE} y {@code BLOCKED}.
     *
     * <p>
     * Representa una operación de negocio explícita.
     * </p>
     */
    public void toggleStatus() {
        this.status = (this.status == CardStatus.ACTIVE)
                ? CardStatus.BLOCKED
                : CardStatus.ACTIVE;
    }
}
