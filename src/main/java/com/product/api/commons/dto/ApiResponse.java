package com.product.api.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase genérica para respuestas de la API.
 * Contiene un mensaje descriptivo de la operación realizada.
 */
public class ApiResponse {

    @JsonProperty("message")
    private String message;

    // Constructor vacío
    public ApiResponse() {
    }

    // Constructor con mensaje
    public ApiResponse(String message) {
        this.message = message;
    }

    // ===== Getter y Setter =====

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}