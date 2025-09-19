package com.product.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Manejador de excepciones para toda la aplicación.
 * Toma las excepciones hechas por los controladores y las convierte
 * en respuestas JSON.
 *
 * @ControllerAdvice aplica lógica a los demás @RestController de la aplicación.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Maneja las excepciones del tipo {@link ApiException}.
     *
     * @param ex      La excepción ApiException capturada.
     * @param request El contexto de la petición web actual.
     * @return Un ResponseEntity que contiene el cuerpo del error (ExceptionResponse)
     * y el código de estado HTTP correspondiente.
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        
        ExceptionResponse responseBody = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getStatus(),
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return new ResponseEntity<>(responseBody, responseBody.getError());
    }

    /**
     * "Último recurso" para cualquier excepción no capturada previamente.
     * Previene que el servidor devuelva errores HTML 
     * y asegura una respuesta JSON incluso para errores inesperados.
     *
     * @param ex      La excepción genérica capturada.
     * @param request El contexto de la petición web actual.
     * @return Un ResponseEntity con un error 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionResponse responseBody = new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status,
                "Ocurrió un error interno inesperado en el servidor.",
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return new ResponseEntity<>(responseBody, responseBody.getError());
    }
}