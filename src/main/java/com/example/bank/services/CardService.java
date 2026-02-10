package com.example.bank.services;

import java.time.YearMonth;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;
import java.time.YearMonth;
import java.time.LocalDate;

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
}
