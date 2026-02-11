package com.example.bank.controller;

import com.example.bank.dto.CardDetailsResponse;
import com.example.bank.dto.CardResponse;
import com.example.bank.dto.CreateCardRequest;
import com.example.bank.dto.PurchaseRequest;
import com.example.bank.dto.RechargeRequest;
import com.example.bank.dto.TransactionResponse;
import com.example.bank.entity.Card;
import com.example.bank.services.CardService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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
            @Valid @RequestBody CreateCardRequest request) {

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
    
    /**
     * Realiza una recarga de saldo a una tarjeta bancaria existente.
     *
     * <p>
     * Esta operación incrementa el balance disponible de la tarjeta
     * siempre que la misma se encuentre en estado {@code ACTIVE}.
     * </p>
     *
     * <p>
     * Validaciones aplicadas:
     * <ul>
     *     <li>La tarjeta debe existir.</li>
     *     <li>La tarjeta debe estar activa.</li>
     *     <li>El monto debe ser mayor que cero.</li>
     * </ul>
     * </p>
     *
     * @param cardNumber número único de la tarjeta a recargar
     * @param request    objeto que contiene el monto a recargar
     * @return representación actualizada de la tarjeta con el nuevo balance
     * @throws IllegalArgumentException si la tarjeta no existe o el monto es inválido
     * @throws IllegalStateException    si la tarjeta no se encuentra activa
     */
    @Operation(
        summary = "Recargar saldo",
        description = "Recarga saldo a una tarjeta activa"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Recarga realizada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CardResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tarjeta no encontrada",
            content = @Content
        )
    })
    @PutMapping("/{cardNumber}/recharge")
    public CardResponse recharge(
            @PathVariable String cardNumber,
            @RequestBody RechargeRequest request) {

        Card card = cardService.recharge(cardNumber, request.getAmount());
        return new CardResponse(card);
    }

    /**
     * Realiza una compra utilizando una tarjeta bancaria.
     *
     * <p>
     * La tarjeta debe estar activa y contar con saldo suficiente.
     * </p>
     *
     * @param cardNumber número de la tarjeta
     * @param request datos de la compra
     * @return representación actualizada de la tarjeta
     */
    @Operation(
        summary = "Realizar compra",
        description = "Descuenta saldo de una tarjeta activa si dispone de fondos suficientes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Compra realizada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CardResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida o saldo insuficiente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tarjeta no encontrada",
            content = @Content
        )
    })
    @PutMapping("/{cardNumber}/purchase")
    public CardResponse purchase(
            @PathVariable String cardNumber,
            @RequestBody PurchaseRequest request) {

        Card card = cardService.purchase(cardNumber, request.getAmount());
        return new CardResponse(card);
    }

    /**
     * Consulta el saldo actual y el historial completo de movimientos
     * de una tarjeta bancaria.
     *
     * <p>
     * Devuelve toda la información operativa disponible incluyendo:
     * número, tipo, titular, fecha de expiración, estado,
     * balance actual y lista de movimientos realizados.
     * </p>
     *
     * @param cardNumber número único de la tarjeta
     * @return información detallada de la tarjeta
     */
    @Operation(
        summary = "Consultar saldo e historial",
        description = "Obtiene toda la información de la tarjeta incluyendo balance actual y movimientos"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Información obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CardDetailsResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tarjeta no encontrada",
            content = @Content
        )
    })
    @GetMapping("infoCard/{cardNumber}")
    public CardDetailsResponse getCardDetails(
            @PathVariable String cardNumber) {

        Card card = cardService.getCardDetails(cardNumber);

        List<TransactionResponse> transactions =
                card.getTransactions().stream()
                        .map(t -> new TransactionResponse(
                                t.getType().name(),
                                t.getAmount(),
                                t.getTimestamp()))
                        .toList();

        return new CardDetailsResponse(
                card.getCardNumber(),
                card.getCardType(),
                card.getHolderName(),
                card.getExpirationDate(),
                card.getBalance(),
                card.getStatus().name(),
                transactions
        );
    }

}
