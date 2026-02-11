package com.example.bank.entity;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.bank.enums.CardStatus;
import com.example.bank.enums.TransactionType;

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
     * Transacciones.
     */
    private final List<Transaction> transactions = new ArrayList<>();


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
        this.status = CardStatus.BLOCKED;
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
     * @return Transacciones
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
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
    
    /**
     * Incrementa el balance de la tarjeta mediante una operación de recarga.
     *
     * <p>
     * Esta operación solo puede ejecutarse si la tarjeta se encuentra
     * en estado {@link CardStatus#ACTIVE}. Además, el monto proporcionado
     * debe ser mayor que cero.
     * </p>
     *
     * <p>
     * En caso de incumplir las reglas de negocio, se lanzará una excepción
     * y no se modificará el estado interno de la entidad.
     * </p>
     *
     * @param amount monto a adicionar al balance actual de la tarjeta
     * @throws IllegalStateException si la tarjeta no se encuentra activa
     * @throws IllegalArgumentException si el monto es menor o igual a cero
     */
    public void recharge(double amount) {

        if (this.status != CardStatus.ACTIVE) {
            throw new IllegalStateException("Card must be ACTIVE to recharge");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        this.balance += amount;

        transactions.add(new Transaction(TransactionType.RECHARGE, amount));

    }

    /**
     * Realiza una compra descontando saldo disponible de la tarjeta.
     *
     * <p>
     * La operación solo puede ejecutarse si la tarjeta se encuentra
     * en estado {@link CardStatus#ACTIVE} y dispone de saldo suficiente.
     * El balance resultante no puede ser negativo.
     * </p>
     *
     * @param amount monto de la compra
     * @throws IllegalStateException si la tarjeta no está activa
     * @throws IllegalArgumentException si el monto es menor o igual a cero
     *                                  o si no hay saldo suficiente
     */
    public void purchase(double amount) {

        if (this.status != CardStatus.ACTIVE) {
            throw new IllegalStateException("Card must be ACTIVE to make a purchase");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.balance -= amount;

        transactions.add(new Transaction(TransactionType.PURCHASE, amount));

    }

}
