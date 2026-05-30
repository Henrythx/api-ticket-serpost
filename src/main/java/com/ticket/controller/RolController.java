package com.ticket.controller;

import com.ticket.dto.usuario.rol.*;
import com.ticket.services.interfaces.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> crearRol(@Valid @RequestBody CreateRolDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.crearRol(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> actualizarRol(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateRolDTO dto) {
        dto.setIdRol(id);
        return ResponseEntity.ok(rolService.actualizarRol(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerRol(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.obtenerRolPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }
}

