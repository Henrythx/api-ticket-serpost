package com.ticket.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de diagnóstico (health-check).
 *
 * <p>Permite comprobar rápidamente que el servicio web está operativo. Es útil
 * como evidencia de "servicio corriendo en localhost" exigida en el avance del
 * proyecto.</p>
 *
 * <p>Ruta efectiva: {@code GET /api/health} (el prefijo {@code /api} proviene del
 * {@code server.servlet.context-path}).</p>
 */
@RestController
@RequestMapping("/health")
public class PruebasController {

    /**
     * Devuelve el estado del servicio.
     *
     * @return objeto JSON con el estado, el nombre del servicio y la marca de tiempo.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> estado() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "servicio", "API Sistema de Tickets SERPOST",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
