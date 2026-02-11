package com.example.bank.services;

import java.time.YearMonth;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;
/**
 * Servicio de dominio encargado de la lógica de tarjetas bancarias.
 *
 * <p>
 * Este servicio encapsula las reglas de negocio relacionadas con la creación
 * y gestión de tarjetas. No tiene conocimiento de la capa de presentación
 * ni de la infraestructura, lo que permite una alta cohesión y bajo acoplamiento.
 * </p>
 */
@Service
public class CardService {

    private final Map<String, Card> cardStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public Card createCard(CreateCardRequest request) {

        YearMonth expiration = YearMonth.of(
                request.getExpirationYear(),
                request.getExpirationMonth()
        );

        YearMonth minAllowed = YearMonth.now().plusYears(3);

        if (expiration.isBefore(minAllowed)) {
            throw new IllegalArgumentException(
                "Expiration date must be at least 3 years from now"
            );
        }
        String holderName = request.getHolderName();

        if (holderName == null || holderName.trim().length() < 15) {
            throw new IllegalArgumentException(
                "Holder name must be at least 15 characters long"
            );
        }

        String kindString = request.getKindMoney();

        if (kindString == null || kindString.isEmpty() || !kindString.equals("USD")) {
            throw new IllegalArgumentException(
                "Kind of money must be USD"
            );
        }

        String cardNumber = generateCardNumber();
        Card card = new Card(cardNumber, request.getHolderName(), expiration);
        cardStore.put(cardNumber, card);
        return card;
    }

    /**
     * Alterna el estado de una tarjeta existente (por ejemplo, activa/inactiva).
     *
     * @param cardNumber número de la tarjeta a modificar
     * @return tarjeta con el estado actualizado
     * @throws IllegalArgumentException si la tarjeta no existe
     */
    public Card toggleCardStatus(String cardNumber) {
        Card card = cardStore.get(cardNumber);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        card.toggleStatus();
        return card;
    }

    /**
     * Genera un número de tarjeta de 16 dígitos.
     *
     * <p>Utiliza un prefijo fijo (ejemplo VISA) y completa el resto
     * con dígitos aleatorios.
     *
     * <p>No implementa validación Luhn ni garantiza unicidad global.
     *
     * @return número de tarjeta generado
     */
    private String generateCardNumber() {
        String prefix = "411111"; // ejemplo: VISA
        StringBuilder sb = new StringBuilder(prefix);

        while (sb.length() < 16) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Realiza una recarga de saldo sobre una tarjeta existente.
     *
     * <p>
     * Busca la tarjeta por su número único y delega en la entidad
     * {@link Card} la validación de estado y monto antes de aplicar
     * la recarga.
     * </p>
     *
     * <p>
     * Reglas aplicadas:
     * <ul>
     *     <li>La tarjeta debe existir en el almacenamiento.</li>
     *     <li>La validación de estado (ACTIVE) y monto (> 0)
     *         es responsabilidad de la entidad.</li>
     * </ul>
     * </p>
     *
     * @param cardNumber número único de la tarjeta a recargar
     * @param amount     monto a incrementar en el balance
     * @return entidad {@link Card} actualizada con el nuevo balance
     * @throws IllegalArgumentException si la tarjeta no existe
     * @throws IllegalStateException    si la tarjeta no está activa
     */
    public Card recharge(String cardNumber, double amount) {

        Card card = cardStore.get(cardNumber);

        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        card.recharge(amount);
        return card;
    }
    
    /**
     * Ejecuta una compra sobre una tarjeta existente.
     *
     * @param cardNumber número único de la tarjeta
     * @param amount monto de la compra
     * @return tarjeta actualizada tras la operación
     * @throws IllegalArgumentException si la tarjeta no existe
     */
    public Card purchase(String cardNumber, double amount) {

        Card card = cardStore.get(cardNumber);

        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        card.purchase(amount);
        return card;
    }

        /**
     * Obtiene la información completa de una tarjeta incluyendo historial.
     *
     * @param cardNumber número único de la tarjeta
     * @return tarjeta encontrada
     * @throws IllegalArgumentException si la tarjeta no existe
     */
    public Card getCardDetails(String cardNumber) {

        Card card = cardStore.get(cardNumber);

        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        return card;
    }

}
