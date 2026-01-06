package com.example.bank.controller;

import com.example.bank.dto.CardResponse;
import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;
import com.example.bank.services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@RequestBody CreateCardRequest request) {
        Card card = cardService.createCard(request);
        return new CardResponse(card);
    }

    @PutMapping("/{cardNumber}/toggle-status")
    public CardResponse toggleStatus(@PathVariable String cardNumber) {
        Card card = cardService.toggleCardStatus(cardNumber);
        return new CardResponse(card);
    }
}
