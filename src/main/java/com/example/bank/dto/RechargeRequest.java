package com.example.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de entrada para realizar una recarga de saldo a una tarjeta bancaria.
 *
 * <p>
 * Contiene el monto que será acreditado al balance de una tarjeta existente.
 * La validación de reglas de negocio como estado de la tarjeta y monto válido
 * es responsabilidad de la capa de servicio y del dominio.
 * </p>
 */
@Schema(
        name = "RechargeRequest",
        description = "Solicitud utilizada para recargar saldo a una tarjeta bancaria activa"
)
public class RechargeRequest {

    /**
     * Monto que será sumado al balance actual de la tarjeta.
     *
     * <p>
     * Debe ser un valor mayor que cero. La validación es aplicada
     * en la capa de servicio y/o dominio.
     * </p>
     */
    @Schema(
            description = "Monto a recargar en la tarjeta",
            example = "200.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private double amount;

    /**
     * @return monto solicitado para la recarga
     */
    public double getAmount() {
        return amount;
    }
}
