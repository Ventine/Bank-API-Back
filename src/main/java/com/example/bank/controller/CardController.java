package com.example.bank.controller;

import com.example.bank.dto.CardResponse;
import com.example.bank.dto.CreateCardRequest;
import com.example.bank.entity.Card;
import com.example.bank.services.CardService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * CardController
 *
 * <p>Controlador responsable de la gestión del ciclo de vida de tarjetas
 * bancarias. Expone operaciones para creación de tarjetas y cambio de estado
 * operativo.</p>
 *
 * <p>Este controlador actúa como capa de entrada HTTP y delega toda la
 * lógica de negocio al {@link CardService}.</p>
 */
@RestController
@RequestMapping("/cards")
public class CardController {

    /**
     * Servicio de dominio encargado de la lógica de tarjetas.
     */
    private final CardService cardService;

    /**
     * Constructor explícito para inyección de dependencias.
     *
     * @param cardService servicio de tarjetas
     */
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Crea una nueva tarjeta bancaria.
     *
     * <p>Recibe la información necesaria para la creación de una tarjeta
     * y retorna el estado inicial de la misma una vez persistida.</p>
     *
     * @param request datos necesarios para crear la tarjeta
     * @return representación de la tarjeta creada
     */
    @Operation(
        summary = "Crear una nueva tarjeta",
        description = "Crea una tarjeta bancaria con la información proporcionada "
                    + "y retorna su estado inicial."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Tarjeta creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CardResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos requeridos para la creación de la tarjeta",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateCardRequest.class)
                )
            )
            @RequestBody CreateCardRequest request) {

        Card card = cardService.createCard(request);
        return new CardResponse(card);
    }

    /**
     * Alterna el estado de una tarjeta bancaria.
     *
     * <p>Permite habilitar o deshabilitar una tarjeta existente en función
     * de su estado actual.</p>
     *
     * @param cardNumber número único de la tarjeta
     * @return representación actualizada de la tarjeta
     */
    @Operation(
        summary = "Alternar estado de una tarjeta",
        description = "Cambia el estado de una tarjeta bancaria entre activa "
                    + "e inactiva utilizando su número como identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Estado de la tarjeta actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CardResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tarjeta no encontrada",
            content = @Content
        )
    })
    @PutMapping("/{cardNumber}/toggle-status")
    public CardResponse toggleStatus(
            @Parameter(
                description = "Número de la tarjeta a actualizar",
                required = true,
                example = "4111111111111111"
            )
            @PathVariable String cardNumber) {

        Card card = cardService.toggleCardStatus(cardNumber);
        return new CardResponse(card);
    }
}
