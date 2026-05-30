package com.ticket.controller;

import com.ticket.dto.ticket.otros.PrioridadDTO;
import com.ticket.services.interfaces.PrioridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/prioridades")
public class PrioridadController {

    @Autowired
    private PrioridadService prioridadService;

    @PostMapping
    public ResponseEntity<PrioridadDTO> crear(@Valid @RequestBody PrioridadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prioridadService.crearPrioridad(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrioridadDTO> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody PrioridadDTO dto) {
        return ResponseEntity.ok(prioridadService.actualizarPrioridad(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrioridadDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(prioridadService.obtenerPrioridad(id));
    }

    @GetMapping
    public ResponseEntity<List<PrioridadDTO>> listar() {
        return ResponseEntity.ok(prioridadService.listarPrioridades());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prioridadService.eliminarPrioridad(id);
        return ResponseEntity.noContent().build();
    }
}
