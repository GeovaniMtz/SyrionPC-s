package com.product.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Una excepción personalizada para manejar errores específicos de la API.
 * Encapsula un estado HTTP junto con un mensaje descriptivo del error.
 */
public class ApiException extends RuntimeException {

    /**
     * Número de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * El código de estado HTTP asociado con la excepción.
     */
    private final HttpStatus status;

    /**
     * Constructor para crear una nueva ApiException.
     *
     * @param status  El HttpStatus que representa el tipo de error.
     * @param message Un mensaje descriptivo que detalla la causa del error.
     */
    public ApiException(HttpStatus status, String message) {
        // Llama al constructor de la clase padre (RuntimeException) para establecer el mensaje.
        super(message);
        this.status = status;
    }

    /**
     * Obtiene el estado HTTP de la excepción.
     *
     * @return El HttpStatus de la excepción.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
