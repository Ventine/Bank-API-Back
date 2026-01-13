package com.example.bank.services;

import java.time.YearMonth;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;

/**
 * Servicio de dominio encargado de la gestión de tarjetas.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Creación de tarjetas</li>
 *   <li>Generación de números de tarjeta</li>
 *   <li>Gestión del estado de la tarjeta</li>
 * </ul>
 *
 * <p>Este servicio mantiene un almacenamiento en memoria
 * únicamente con fines demostrativos. No representa persistencia real.
 */
@Service
public class CardService {

    /**
     * Almacenamiento en memoria de tarjetas indexadas por número de tarjeta.
     * Thread-safe para soportar acceso concurrente.
     */
    private final Map<String, Card> cardStore = new ConcurrentHashMap<>();

    /**
     * Generador pseudoaleatorio utilizado para la creación de números de tarjeta.
     */
    private final Random random = new Random();

    /**
     * Crea una nueva tarjeta a partir de la solicitud proporcionada.
     *
     * @param request objeto que contiene los datos necesarios para la creación
     *                de la tarjeta (titular y fecha de expiración)
     * @return tarjeta creada y almacenada en memoria
     */
    public Card createCard(CreateCardRequest request) {
        String cardNumber = generateCardNumber();
        YearMonth expiration = YearMonth.of(
                request.getExpirationYear(),
                request.getExpirationMonth()
        );

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
}
