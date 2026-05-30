package com.ticket.controller;

import com.ticket.dto.usuario.area.*;
import com.ticket.services.interfaces.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public ResponseEntity<AreaResponseDTO> crearArea(@Valid @RequestBody CreateAreaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(areaService.crearArea(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> actualizarArea(@PathVariable Long id,
                                                          @Valid @RequestBody UpdateAreaDTO dto) {
        dto.setIdArea(id);
        return ResponseEntity.ok(areaService.actualizarArea(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> obtenerArea(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.obtenerAreaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AreaResponseDTO>> listarAreas() {
        return ResponseEntity.ok(areaService.listarAreas());
    }
}
