package com.ticket.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ticket.dto.ticket.AdjuntoTicketDTO;
import com.ticket.services.interfaces.AdjuntoService;

/**
 * Controlador REST de adjuntos (evidencias) de tickets.
 *
 * <ul>
 *   <li>{@code POST /api/tickets/{id}/adjuntos} — subir archivo (multipart)</li>
 *   <li>{@code GET /api/tickets/{id}/adjuntos} — listar adjuntos</li>
 *   <li>{@code GET /api/adjuntos/{id}/descargar} — descargar archivo</li>
 * </ul>
 */
@RestController
public class AdjuntoController {

    private final AdjuntoService adjuntoService;

    public AdjuntoController(AdjuntoService adjuntoService) {
        this.adjuntoService = adjuntoService;
    }

    /**
     * Sube un archivo adjunto a un ticket.
     *
     * @param id        ticket destino.
     * @param archivo   archivo (multipart, campo "archivo").
     * @param idUsuario usuario que adjunta (opcional).
     * @return metadatos del adjunto creado (201).
     */
    @PostMapping("/tickets/{id}/adjuntos")
    public ResponseEntity<AdjuntoTicketDTO> subir(@PathVariable Long id,
                                                  @RequestParam("archivo") MultipartFile archivo,
                                                  @RequestParam(required = false) Long idUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adjuntoService.subir(id, archivo, idUsuario));
    }

    /**
     * Lista los adjuntos de un ticket.
     *
     * @param id ticket.
     * @return metadatos de los adjuntos.
     */
    @GetMapping("/tickets/{id}/adjuntos")
    public ResponseEntity<List<AdjuntoTicketDTO>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(adjuntoService.listar(id));
    }

    /**
     * Descarga el contenido de un adjunto.
     *
     * @param id adjunto.
     * @return archivo binario como descarga.
     */
    @GetMapping("/adjuntos/{id}/descargar")
    public ResponseEntity<byte[]> descargar(@PathVariable Long id) {
        AdjuntoService.ArchivoDescarga archivo = adjuntoService.descargar(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(archivo.tipoContenido()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + archivo.nombre() + "\"")
                .body(archivo.contenido());
    }
}
