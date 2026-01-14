package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación bancaria.
 *
 * <p>
 * Inicializa el contexto de Spring Boot, realiza el escaneo de componentes
 * y aplica la auto-configuración definida por el framework.
 * </p>
 *
 * <p>
 * No contiene lógica de negocio. Su única responsabilidad es el arranque
 * de la aplicación.
 * </p>
 */
@SpringBootApplication
public class BankApplication {

    /**
     * Método principal de arranque.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
