package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.common.PaginatedResponse;
import com.ticket.dto.ticket.AccionTicketDTO;
import com.ticket.dto.ticket.CambiarEstadoTicketDTO;
import com.ticket.dto.ticket.CreateTicketDTO;
import com.ticket.dto.ticket.HistorialTicketDTO;
import com.ticket.dto.ticket.TicketResponseDTO;

/**
 * Servicio de negocio del agregado Ticket.
 *
 * <p>Concentra las reglas operativas del sistema (capa de lógica de negocio del
 * diseño SOA): validación de campos, asignación automática de técnico, cálculo de
 * SLA, control del flujo de estados, registro de historial y disparo de
 * notificaciones. No accede directamente a la base de datos: delega en los
 * repositorios.</p>
 */
public interface TicketService {

    /**
     * Registra un nuevo ticket creado por el usuario solicitante, aplicando toda
     * la automatización: prioridad por defecto, asignación de técnico, cálculo de
     * SLA, estado inicial {@code ABIERTO} e historial de creación.
     *
     * @param dto datos del ticket a registrar.
     * @return el ticket creado.
     */
    TicketResponseDTO crear(CreateTicketDTO dto);

    /**
     * Lista tickets con filtros opcionales y paginación en memoria.
     *
     * @param estado        filtro por código de estado (opcional).
     * @param idTecnico     filtro por técnico asignado (opcional).
     * @param idSolicitante filtro por usuario solicitante (opcional).
     * @param idPrioridad   filtro por prioridad (opcional).
     * @param idArea        filtro por área (opcional).
     * @param page          número de página (1-based).
     * @param perPage       tamaño de página.
     * @return página de tickets que cumplen los filtros.
     */
    PaginatedResponse<TicketResponseDTO> listar(String estado, Long idTecnico, Long idSolicitante,
                                                Long idPrioridad, Long idArea, int page, int perPage);

    /**
     * Reasigna un ticket a otro técnico (acción del administrador).
     *
     * @param id        identificador del ticket.
     * @param idTecnico nuevo técnico.
     * @return el ticket actualizado.
     */
    TicketResponseDTO reasignarTecnico(Long id, Long idTecnico);

    /**
     * Cambia la prioridad de un ticket y recalcula su vencimiento de SLA.
     *
     * @param id          identificador del ticket.
     * @param idPrioridad nueva prioridad.
     * @return el ticket actualizado.
     */
    TicketResponseDTO cambiarPrioridad(Long id, Long idPrioridad);

    /**
     * Obtiene el detalle de un ticket por su identificador.
     *
     * @param id identificador del ticket.
     * @return el ticket.
     */
    TicketResponseDTO obtener(Long id);

    /**
     * Marca un ticket como {@code EN_ATENCION} (el técnico inicia el trabajo).
     *
     * @param id        identificador del ticket.
     * @param idTecnico técnico que atiende (opcional; responsable del evento).
     * @return el ticket actualizado.
     */
    TicketResponseDTO atender(Long id, Long idTecnico);

    /**
     * Resuelve/cierra un ticket adjuntando el comentario de solución.
     *
     * @param id  identificador del ticket.
     * @param dto comentario de cierre y usuario responsable.
     * @return el ticket actualizado.
     */
    TicketResponseDTO cerrar(Long id, AccionTicketDTO dto);

    /**
     * Cambia el estado de un ticket validando la transición del ciclo de vida.
     *
     * @param id  identificador del ticket.
     * @param dto estado destino, comentario y usuario responsable.
     * @return el ticket actualizado.
     */
    TicketResponseDTO cambiarEstado(Long id, CambiarEstadoTicketDTO dto);

    /**
     * Devuelve la línea de tiempo (historial) de un ticket.
     *
     * @param idTicket identificador del ticket.
     * @return eventos del historial en orden cronológico.
     */
    List<HistorialTicketDTO> historial(Long idTicket);

    /**
     * Agrega un comentario/evento al historial de un ticket sin cambiar su estado.
     *
     * @param idTicket identificador del ticket.
     * @param dto      comentario, tipo de evento y usuario responsable.
     * @return el evento de historial creado.
     */
    HistorialTicketDTO agregarComentario(Long idTicket, AccionTicketDTO dto);
}
