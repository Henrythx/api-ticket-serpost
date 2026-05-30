package com.ticket.controller;

import com.ticket.dto.ticket.otros.CategoriaTicketDTO;
import com.ticket.services.interfaces.CategoriaTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaTicketController {

    @Autowired
    private CategoriaTicketService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaTicketDTO> crear(@Valid @RequestBody CategoriaTicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaTicketDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CategoriaTicketDTO dto) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaTicketDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaTicketDTO>> listar() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
