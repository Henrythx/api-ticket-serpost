package com.ticket.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.ticket.CreateSlaDTO;
import com.ticket.dto.ticket.SlaResponseDTO;
import com.ticket.dto.ticket.UpdateSlaDTO;
import com.ticket.services.interfaces.SlaService;

import jakarta.validation.Valid;

/**
 * Controlador REST de las reglas de SLA.
 *
 * <p>Rutas bajo el contexto {@code /api}:</p>
 * <ul>
 *   <li>{@code GET /api/sla} — listar reglas</li>
 *   <li>{@code PUT /api/sla/{id}} — actualizar tiempos</li>
 *   <li>{@code DELETE /api/sla/{id}} — eliminar regla</li>
 * </ul>
 */
@RestController
@RequestMapping("/sla")
public class SlaController {

    private final SlaService slaService;

    public SlaController(SlaService slaService) {
        this.slaService = slaService;
    }

    /**
     * Lista todas las reglas de SLA.
     *
     * @return colección de reglas.
     */
    @GetMapping
    public ResponseEntity<List<SlaResponseDTO>> listar() {
        return ResponseEntity.ok(slaService.listar());
    }

    /**
     * Crea una nueva regla de SLA.
     *
     * @param dto datos de la regla.
     * @return la regla creada (201).
     */
    @PostMapping
    public ResponseEntity<SlaResponseDTO> crear(@Valid @RequestBody CreateSlaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(slaService.crear(dto));
    }

    /**
     * Activa o inactiva una regla de SLA.
     *
     * @param id     identificador de la regla.
     * @param activo nuevo estado.
     * @return la regla actualizada.
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<SlaResponseDTO> cambiarEstado(@PathVariable Long id,
                                                        @RequestParam boolean activo) {
        return ResponseEntity.ok(slaService.cambiarEstado(id, activo));
    }

    /**
     * Actualiza los tiempos de una regla de SLA.
     *
     * @param id  identificador de la regla.
     * @param dto nuevos tiempos.
     * @return la regla actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SlaResponseDTO> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody UpdateSlaDTO dto) {
        return ResponseEntity.ok(slaService.actualizar(id, dto));
    }

    /**
     * Elimina una regla de SLA.
     *
     * @param id identificador de la regla.
     * @return 204 sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        slaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
