package com.product.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * Estructura estándar de una respuesta de error en la API.
 * Este objeto se serializará a JSON cuando ocurra una excepción.
 */
public class ExceptionResponse {

    /**
     * Fecha y hora exactas en que ocurrió la excepción.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * El código de estado HTTP como un número.
     */
    private Integer status;

    /**
     * La definición/nombre del estado HTTP. 
     */
    private HttpStatus error;

    /**
     * Mensaje descriptivo y legible que detalla la causa del error.
     */
    private String message;

    /**
     * El endpoint de la solicitud que originó el error.
     */
    private String path;

    /**
     * Constructor para inicializar todos los campos de la respuesta de excepción.
     *
     * @param timestamp La fecha y hora del error.
     * @param status    El código de estado HTTP numérico.
     * @param error     El objeto HttpStatus.
     * @param message   La descripción del error.
     * @param path      La ruta de la solicitud.
     */
    public ExceptionResponse(LocalDateTime timestamp, Integer status, HttpStatus error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // --- GETTERS & SETTERS ---

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}