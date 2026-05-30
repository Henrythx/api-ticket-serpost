package com.ticket.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.common.PaginatedResponse;
import com.ticket.dto.ticket.AccionTicketDTO;
import com.ticket.dto.ticket.CambiarEstadoTicketDTO;
import com.ticket.dto.ticket.CreateTicketDTO;
import com.ticket.dto.ticket.HistorialTicketDTO;
import com.ticket.dto.ticket.TicketResponseDTO;
import com.ticket.services.interfaces.ExportService;
import com.ticket.services.interfaces.TicketService;

import jakarta.validation.Valid;

/**
 * Controlador REST del módulo de tickets (capa de presentación/contrato).
 *
 * <p>Es el punto de entrada del servicio web para el ciclo de vida de un ticket.
 * Delega toda la lógica en {@link TicketService} y se limita a traducir entre
 * HTTP y la capa de negocio. Rutas bajo el contexto {@code /api}:</p>
 * <table border="1">
 *   <caption>Endpoints expuestos</caption>
 *   <tr><th>Método</th><th>Ruta</th><th>Descripción</th></tr>
 *   <tr><td>POST</td><td>/api/tickets</td><td>Registrar un ticket</td></tr>
 *   <tr><td>GET</td><td>/api/tickets</td><td>Listar/filtrar tickets</td></tr>
 *   <tr><td>GET</td><td>/api/tickets/assigned</td><td>Tickets asignados a un técnico</td></tr>
 *   <tr><td>GET</td><td>/api/tickets/{id}</td><td>Detalle de un ticket</td></tr>
 *   <tr><td>POST</td><td>/api/tickets/{id}/atender</td><td>Iniciar atención</td></tr>
 *   <tr><td>POST</td><td>/api/tickets/{id}/cerrar</td><td>Resolver/cerrar</td></tr>
 *   <tr><td>PUT</td><td>/api/tickets/{id}</td><td>Cambiar estado</td></tr>
 *   <tr><td>GET</td><td>/api/tickets/{id}/historial</td><td>Historial</td></tr>
 *   <tr><td>POST</td><td>/api/tickets/{id}/comentarios</td><td>Agregar comentario</td></tr>
 * </table>
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ExportService exportService;

    public TicketController(TicketService ticketService, ExportService exportService) {
        this.ticketService = ticketService;
        this.exportService = exportService;
    }

    /**
     * Registra un nuevo ticket. Responde 201 con el ticket creado.
     *
     * @param dto datos del ticket.
     * @return el ticket registrado.
     */
    @PostMapping
    public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody CreateTicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crear(dto));
    }

    /**
     * Lista tickets con filtros opcionales y paginación.
     *
     * @param estado        filtro por código de estado.
     * @param idTecnico     filtro por técnico asignado.
     * @param idSolicitante filtro por solicitante.
     * @param page          página (1-based).
     * @param perPage       tamaño de página.
     * @return página de tickets.
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<TicketResponseDTO>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long idTecnico,
            @RequestParam(required = false) Long idSolicitante,
            @RequestParam(required = false) Long idPrioridad,
            @RequestParam(required = false) Long idArea,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "50") int perPage) {
        return ResponseEntity.ok(
                ticketService.listar(estado, idTecnico, idSolicitante, idPrioridad, idArea, page, perPage));
    }

    /**
     * Lista los tickets asignados a un técnico (bandeja de trabajo).
     *
     * @param estado    filtro por estado.
     * @param idTecnico técnico cuya bandeja se consulta.
     * @param page      página (1-based).
     * @param perPage   tamaño de página.
     * @return página de tickets asignados.
     */
    @GetMapping("/assigned")
    public ResponseEntity<PaginatedResponse<TicketResponseDTO>> listarAsignados(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long idTecnico,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "50") int perPage) {
        return ResponseEntity.ok(ticketService.listar(estado, idTecnico, null, null, null, page, perPage));
    }

    /**
     * Exporta a Excel (.xlsx) los tickets que cumplen los filtros.
     *
     * @return archivo Excel como descarga.
     */
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportarExcel(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long idTecnico,
            @RequestParam(required = false) Long idSolicitante,
            @RequestParam(required = false) Long idPrioridad,
            @RequestParam(required = false) Long idArea) {
        List<TicketResponseDTO> tickets = ticketService
                .listar(estado, idTecnico, idSolicitante, idPrioridad, idArea, 1, Integer.MAX_VALUE).getData();
        byte[] contenido = exportService.exportarTicketsExcel(tickets);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tickets.xlsx\"")
                .body(contenido);
    }

    /**
     * Exporta a PDF los tickets que cumplen los filtros.
     *
     * @return archivo PDF como descarga.
     */
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportarPdf(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long idTecnico,
            @RequestParam(required = false) Long idSolicitante,
            @RequestParam(required = false) Long idPrioridad,
            @RequestParam(required = false) Long idArea) {
        List<TicketResponseDTO> tickets = ticketService
                .listar(estado, idTecnico, idSolicitante, idPrioridad, idArea, 1, Integer.MAX_VALUE).getData();
        byte[] contenido = exportService.exportarTicketsPdf(tickets);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tickets.pdf\"")
                .body(contenido);
    }

    /**
     * Devuelve el detalle de un ticket.
     *
     * @param id identificador del ticket.
     * @return el ticket.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.obtener(id));
    }

    /**
     * Marca un ticket como en atención.
     *
     * @param id        identificador del ticket.
     * @param idTecnico técnico que atiende (opcional).
     * @return el ticket actualizado.
     */
    @PostMapping("/{id}/atender")
    public ResponseEntity<TicketResponseDTO> atender(@PathVariable Long id,
                                                     @RequestParam(required = false) Long idTecnico) {
        return ResponseEntity.ok(ticketService.atender(id, idTecnico));
    }

    /**
     * Resuelve/cierra un ticket con un comentario de solución.
     *
     * @param id  identificador del ticket.
     * @param dto comentario de cierre (opcional el cuerpo).
     * @return el ticket actualizado.
     */
    @PostMapping("/{id}/cerrar")
    public ResponseEntity<TicketResponseDTO> cerrar(@PathVariable Long id,
                                                    @RequestBody(required = false) AccionTicketDTO dto) {
        return ResponseEntity.ok(ticketService.cerrar(id, dto));
    }

    /**
     * Cambia el estado de un ticket validando el flujo del ciclo de vida.
     *
     * @param id  identificador del ticket.
     * @param dto estado destino y comentario.
     * @return el ticket actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> cambiarEstado(@PathVariable Long id,
                                                           @Valid @RequestBody CambiarEstadoTicketDTO dto) {
        return ResponseEntity.ok(ticketService.cambiarEstado(id, dto));
    }

    /**
     * Reasigna el ticket a otro técnico (acción administrativa).
     *
     * @param id        identificador del ticket.
     * @param idTecnico nuevo técnico.
     * @return el ticket actualizado.
     */
    @PatchMapping("/{id}/tecnico")
    public ResponseEntity<TicketResponseDTO> reasignar(@PathVariable Long id,
                                                       @RequestParam Long idTecnico) {
        return ResponseEntity.ok(ticketService.reasignarTecnico(id, idTecnico));
    }

    /**
     * Cambia la prioridad del ticket y recalcula su SLA.
     *
     * @param id          identificador del ticket.
     * @param idPrioridad nueva prioridad.
     * @return el ticket actualizado.
     */
    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<TicketResponseDTO> cambiarPrioridad(@PathVariable Long id,
                                                              @RequestParam Long idPrioridad) {
        return ResponseEntity.ok(ticketService.cambiarPrioridad(id, idPrioridad));
    }

    /**
     * Devuelve el historial (línea de tiempo) de un ticket.
     *
     * @param id identificador del ticket.
     * @return eventos del historial.
     */
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialTicketDTO>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.historial(id));
    }

    /**
     * Agrega un comentario/evento al historial sin cambiar el estado.
     *
     * @param id  identificador del ticket.
     * @param dto comentario y tipo de evento.
     * @return el evento de historial creado (201).
     */
    @PostMapping("/{id}/comentarios")
    public ResponseEntity<HistorialTicketDTO> comentar(@PathVariable Long id,
                                                       @RequestBody AccionTicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.agregarComentario(id, dto));
    }
}
