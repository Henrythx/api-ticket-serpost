package com.ticket.controller;

import com.ticket.dto.ticket.historialTicket.HistorialTicketDTO;
import com.ticket.services.interfaces.HistorialTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialTicketService historialService;

    // Listar historial por ticket
    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<HistorialTicketDTO>> listarPorTicket(@PathVariable Long idTicket) {
        return ResponseEntity.ok(historialService.listarPorTicket(idTicket));
    }

    // Obtener historial por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistorialTicketDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.obtenerPorId(id));
    }

    // Eliminar historial
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        historialService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}
