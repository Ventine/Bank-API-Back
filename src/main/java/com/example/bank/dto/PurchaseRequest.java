package com.example.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de entrada para realizar una compra con una tarjeta bancaria.
 */
@Schema(
        name = "PurchaseRequest",
        description = "Solicitud para realizar una compra con una tarjeta bancaria"
)
public class PurchaseRequest {

    @Schema(
            description = "Monto de la compra",
            example = "150.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private double amount;

    public double getAmount() {
        return amount;
    }
}
