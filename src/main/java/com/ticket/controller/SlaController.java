package com.ticket.controller;

import com.ticket.dto.ticket.otros.SlaDTO;
import com.ticket.services.interfaces.SlaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sla")
public class SlaController {

    @Autowired
    private SlaService slaService;

    @PostMapping
    public ResponseEntity<SlaDTO> crear(@Valid @RequestBody SlaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(slaService.crearSla(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlaDTO> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody SlaDTO dto) {
        return ResponseEntity.ok(slaService.actualizarSla(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(slaService.obtenerSla(id));
    }

    @GetMapping
    public ResponseEntity<List<SlaDTO>> listar() {
        return ResponseEntity.ok(slaService.listarSla());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        slaService.eliminarSla(id);
        return ResponseEntity.noContent().build();
    }
}
