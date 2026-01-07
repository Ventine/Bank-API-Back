package com.example.bank.services;

import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CardService {

    private final Map<String, Card> cardStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

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

    public Card toggleCardStatus(String cardNumber) {
        Card card = cardStore.get(cardNumber);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        card.toggleStatus();
        return card;
    }

    private String generateCardNumber() {
        String prefix = "411111"; // ejemplo: VISA
        StringBuilder sb = new StringBuilder(prefix);

        while (sb.length() < 16) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
