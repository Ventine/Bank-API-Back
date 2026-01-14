package com.example.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de entrada para la creación de una nueva tarjeta bancaria.
 *
 * <p>
 * Contiene únicamente los datos requeridos desde el cliente para
 * inicializar la tarjeta. Las decisiones de negocio (tipo, número,
 * estado inicial, balance) pertenecen al dominio.
 * </p>
 */
@Schema(
        name = "CreateCardRequest",
        description = "Solicitud para crear una nueva tarjeta bancaria"
)
public class CreateCardRequest {

    @Schema(
            description = "Nombre completo del titular de la tarjeta",
            example = "Juan Pérez",
            required = true
    )
    private String holderName;

    @Schema(
            description = "Año de expiración de la tarjeta",
            example = "2028",
            required = true
    )
    private int expirationYear;

    @Schema(
            description = "Mes de expiración de la tarjeta (1-12)",
            example = "12",
            required = true
    )
    private int expirationMonth;

    /**
     * @return nombre del titular de la tarjeta
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * @return año de expiración
     */
    public int getExpirationYear() {
        return expirationYear;
    }

    /**
     * @return mes de expiración (1-12)
     */
    public int getExpirationMonth() {
        return expirationMonth;
    }
}
