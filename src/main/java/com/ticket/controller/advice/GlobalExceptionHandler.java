package com.ticket.controller.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ticket.model.CustomError;

/**
 * Manejador global de excepciones de la API.
 *
 * <p>Unifica el formato de los errores que devuelve el servicio web (siempre con
 * el campo {@code message}, que es el que consume el frontend), implementando el
 * pilar de Disponibilidad: "manejo controlado de errores, evitando caídas
 * inesperadas y ofreciendo respuestas claras al usuario".</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Traduce los errores de negocio ({@link CustomError}) a su código HTTP.
     *
     * @param ex error de negocio.
     * @return respuesta con el detalle del error.
     */
    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomError ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode());
        body.put("message", ex.getMessage());
        body.put("originClass", ex.getOriginClass());
        body.put("details", ex.getDetails());
        body.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    /**
     * Convierte los fallos de validación de DTOs ({@code @Valid}) en una respuesta
     * 400 con un mensaje consolidado y el detalle por campo.
     *
     * @param ex excepción de validación lanzada por Spring.
     * @return respuesta 400 con los mensajes de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fe -> fe.getField(),
                        fe -> fe.getDefaultMessage() == null ? "valor inválido" : fe.getDefaultMessage(),
                        (a, b) -> a));
        String mensaje = errores.values().stream().collect(Collectors.joining("; "));

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", mensaje.isBlank() ? "Datos inválidos" : mensaje);
        body.put("errors", errores);
        body.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Red de seguridad para cualquier excepción no controlada: responde 500 con un
     * mensaje genérico, evitando exponer detalles internos.
     *
     * @param ex excepción inesperada.
     * @return respuesta 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", "Ocurrió un error interno en el servidor");
        body.put("details", ex.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
