package com.ticket.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ticket.dto.ticket.AdjuntoTicketDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.AdjuntoTicketEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.repositories.jpa.AdjuntoTicketRepository;
import com.ticket.repositories.jpa.TicketRepository;
import com.ticket.services.interfaces.AdjuntoService;
import com.ticket.services.interfaces.AuditoriaService;

import jakarta.transaction.Transactional;

/**
 * Implementación del servicio de adjuntos.
 *
 * <p>Almacena el contenido de los archivos en el sistema de archivos (directorio
 * configurable {@code app.uploads.dir}) con un nombre único, y conserva los
 * metadatos en base de datos. Valida tamaño y existencia del ticket.</p>
 */
@Service
public class AdjuntoServiceImpl implements AdjuntoService {

    private final AdjuntoTicketRepository adjuntoRepository;
    private final TicketRepository ticketRepository;
    private final AuditoriaService auditoriaService;
    private final Path directorioBase;

    public AdjuntoServiceImpl(AdjuntoTicketRepository adjuntoRepository,
                              TicketRepository ticketRepository,
                              AuditoriaService auditoriaService,
                              @Value("${app.uploads.dir:uploads}") String uploadsDir) {
        this.adjuntoRepository = adjuntoRepository;
        this.ticketRepository = ticketRepository;
        this.auditoriaService = auditoriaService;
        this.directorioBase = Paths.get(uploadsDir).toAbsolutePath().normalize();
    }

    @Override
    @Transactional
    public AdjuntoTicketDTO subir(Long idTicket, MultipartFile archivo, Long idUsuario) {
        if (archivo == null || archivo.isEmpty()) {
            throw CustomError.badRequest("El archivo está vacío", "AdjuntoServiceImpl", "archivo");
        }
        TicketEntity ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "AdjuntoServiceImpl"));

        try {
            Files.createDirectories(directorioBase);
            String nombreOriginal = limpiarNombre(archivo.getOriginalFilename());
            String nombreUnico = UUID.randomUUID() + "_" + nombreOriginal;
            Path destino = directorioBase.resolve(nombreUnico).normalize();
            // Seguridad: evitar path traversal fuera del directorio base.
            if (!destino.startsWith(directorioBase)) {
                throw CustomError.badRequest("Nombre de archivo inválido", "AdjuntoServiceImpl", "archivo");
            }
            archivo.transferTo(destino.toFile());

            AdjuntoTicketEntity adj = new AdjuntoTicketEntity();
            adj.setTicket(ticket);
            adj.setNombreArchivo(nombreOriginal);
            adj.setTipoContenido(archivo.getContentType());
            adj.setTamanioBytes(archivo.getSize());
            adj.setRutaAlmacenamiento(destino.toString());
            adj.setIdUsuarioSubida(idUsuario);
            adj.setFechaSubida(LocalDateTime.now());
            AdjuntoTicketEntity guardado = adjuntoRepository.save(adj);

            auditoriaService.registrarActorActual("ADJUNTO", "Ticket #" + idTicket,
                    "Adjuntó el archivo " + nombreOriginal);

            return toDTO(guardado);
        } catch (IOException ex) {
            throw CustomError.internalServer("No se pudo almacenar el archivo", "AdjuntoServiceImpl",
                    ex.getMessage());
        }
    }

    @Override
    public List<AdjuntoTicketDTO> listar(Long idTicket) {
        return adjuntoRepository.findByTicket_IdTicketOrderByFechaSubidaDesc(idTicket).stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ArchivoDescarga descargar(Long idAdjunto) {
        AdjuntoTicketEntity adj = adjuntoRepository.findById(idAdjunto)
                .orElseThrow(() -> CustomError.notFound("Adjunto no encontrado", "AdjuntoServiceImpl"));
        try {
            byte[] contenido = Files.readAllBytes(Paths.get(adj.getRutaAlmacenamiento()));
            String tipo = adj.getTipoContenido() != null
                    ? adj.getTipoContenido()
                    : "application/octet-stream";
            return new ArchivoDescarga(adj.getNombreArchivo(), tipo, contenido);
        } catch (IOException ex) {
            throw CustomError.internalServer("No se pudo leer el archivo", "AdjuntoServiceImpl", ex.getMessage());
        }
    }

    // ─────────────────────────── Helpers ────────────────────────────────────

    /** Sanea el nombre del archivo eliminando rutas y caracteres peligrosos. */
    private String limpiarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "archivo";
        }
        String limpio = Paths.get(nombre).getFileName().toString();
        return limpio.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private AdjuntoTicketDTO toDTO(AdjuntoTicketEntity a) {
        AdjuntoTicketDTO dto = new AdjuntoTicketDTO();
        dto.setIdAdjunto(a.getIdAdjunto());
        dto.setIdTicket(a.getTicket() != null ? a.getTicket().getIdTicket() : null);
        dto.setNombreArchivo(a.getNombreArchivo());
        dto.setTipoContenido(a.getTipoContenido());
        dto.setTamanioBytes(a.getTamanioBytes());
        dto.setIdUsuarioSubida(a.getIdUsuarioSubida());
        dto.setFechaSubida(a.getFechaSubida());
        return dto;
    }
}
