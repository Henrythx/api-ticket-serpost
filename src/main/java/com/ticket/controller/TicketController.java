package com.ticket.controller;

import com.ticket.dto.ticket.ticket.*;
import com.ticket.services.interfaces.TicketService;
import com.ticket.services.interfaces.HistorialTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private HistorialTicketService historialService;

    // Crear ticket
    @PostMapping
    public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody CreateTicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crearTicket(dto));
    }

    // Actualizar ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateTicketDTO dto) {
        return ResponseEntity.ok(ticketService.actualizarTicket(id, dto));
    }

    // Cambiar estado de ticket
    @PatchMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @PathVariable Long idEstado) {
        ticketService.cambiarEstado(id, idEstado);
        return ResponseEntity.noContent().build();
    }

    // Obtener ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.obtenerTicketPorId(id));
    }

    // Listar todos los tickets
    @GetMapping
    public ResponseEntity<List<TicketListDTO>> listar() {
        return ResponseEntity.ok(ticketService.listarTickets());
    }

    // Eliminar ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }

    // Listar historial de un ticket
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<com.ticket.dto.ticket.historialTicket.HistorialTicketDTO>> listarHistorial(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.listarPorTicket(id));
    }
}
