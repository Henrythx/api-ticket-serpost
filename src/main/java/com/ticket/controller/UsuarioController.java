package com.ticket.controller;

import com.ticket.dto.usuario.usuario.*;
import com.ticket.services.interfaces.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody CreateUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }

    @PatchMapping("/estado/cambiar")
    public ResponseEntity<Void> cambiarEstado(@Valid @RequestBody ChangeEstadoUsuarioDTO dto) {
        usuarioService.cambiarEstadoUsuario(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id,
                                                                @Valid @RequestBody UpdateUsuarioDTO dto) {
        dto.setIdUsuario(id);
        return ResponseEntity.ok(usuarioService.actualizarUsuario(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<UsuarioListDTO>> buscarPorArea(@PathVariable Long idArea) {
        return ResponseEntity.ok(usuarioService.buscarPorArea(idArea));
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<UsuarioListDTO>> buscarPorRol(@PathVariable Long idRol) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(idRol));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsuarioListDTO>> buscarPorNombreApellido(@RequestParam String nombre,
                                                                        @RequestParam String apellido) {
        return ResponseEntity.ok(usuarioService.buscarPorNombreApellido(nombre, apellido));
    }
}
