package com.ticket.controller;

import com.ticket.dto.ticket.otros.EstadoTicketDTO;
import com.ticket.services.interfaces.EstadoTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoTicketController {

    @Autowired
    private EstadoTicketService estadoService;

    @PostMapping
    public ResponseEntity<EstadoTicketDTO> crear(@Valid @RequestBody EstadoTicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstado(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoTicketDTO> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody EstadoTicketDTO dto) {
        return ResponseEntity.ok(estadoService.actualizarEstado(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoTicketDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.obtenerEstado(id));
    }

    @GetMapping
    public ResponseEntity<List<EstadoTicketDTO>> listar() {
        return ResponseEntity.ok(estadoService.listarEstados());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estadoService.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
