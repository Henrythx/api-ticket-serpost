package com.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pruebas")
public class PruebasController {
    @GetMapping
    public ResponseEntity<?> saludo() {
        String saludo = "hola";
        return ResponseEntity.ok().body(saludo);
    }
}
