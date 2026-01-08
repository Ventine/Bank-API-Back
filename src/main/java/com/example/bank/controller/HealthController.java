package com.example.bank.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * HealthController
 *
 * <p>Controlador responsable de exponer un endpoint de salud básico de la
 * aplicación. Proporciona información técnica mínima para validación
 * operativa y diagnósticos rápidos.</p>
 *
 * <p>Este endpoint no representa un chequeo profundo de dependencias,
 * sino un indicador de disponibilidad del proceso JVM.</p>
 */
@RestController
public class HealthController {

    /**
     * Nombre de la aplicación obtenido desde la configuración de Spring.
     */
    @Value("${spring.application.name:bank-api}")
    private String applicationName;

    /**
     * Puerto HTTP configurado para la aplicación.
     */
    @Value("${server.port:8080}")
    private String port;

    /**
     * Endpoint de salud de la API.
     *
     * <p>Retorna un conjunto de metadatos de ejecución de la aplicación,
     * incluyendo estado lógico, información del entorno y datos del runtime
     * de Java.</p>
     *
     * @return mapa con el estado de la aplicación y metadatos del sistema
     */
    @Operation(
        summary = "Health check de la aplicación",
        description = "Expone el estado básico de la API y metadatos del runtime. "
                    + "No valida dependencias externas ni recursos internos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "La aplicación está activa y respondiendo",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = """
                    {
                      "status": "UP",
                      "application": "bank-api",
                      "port": "8080",
                      "timestamp": "2026-01-08T15:50:51Z",
                      "javaVersion": "17",
                      "jvm": "OpenJDK 64-Bit Server VM",
                      "os": "Linux",
                      "thread": "http-nio-8080-exec-1"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/healthAPI")
    public Map<String, Object> health() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "UP");
        response.put("application", applicationName);
        response.put("port", port);
        response.put("timestamp", Instant.now().toString());
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("jvm", System.getProperty("java.vm.name"));
        response.put("os", System.getProperty("os.name"));
        response.put("thread", Thread.currentThread().getName());

        return response;
    }
}
