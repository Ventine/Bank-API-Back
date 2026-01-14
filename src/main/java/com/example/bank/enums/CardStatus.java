package com.example.bank.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum que representa los posibles estados de una tarjeta bancaria.
 */
@Schema(
        name = "CardStatus",
        description = "Estado actual de una tarjeta bancaria"
)
public enum CardStatus {

    /**
     * Tarjeta activa y operativa.
     */
    @Schema(description = "Tarjeta activa")
    ACTIVE,

    /**
     * Tarjeta bloqueada por razones operativas o de seguridad.
     */
    @Schema(description = "Tarjeta bloqueada")
    BLOCKED
}
