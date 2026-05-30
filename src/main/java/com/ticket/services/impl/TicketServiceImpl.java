package com.ticket.services.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticket.dto.common.PaginatedResponse;
import com.ticket.dto.ticket.AccionTicketDTO;
import com.ticket.dto.ticket.CambiarEstadoTicketDTO;
import com.ticket.dto.ticket.CreateTicketDTO;
import com.ticket.dto.ticket.HistorialTicketDTO;
import com.ticket.dto.ticket.TicketResponseDTO;
import com.ticket.mapper.TicketMapper;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.model.ticket.HistorialTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.SlaEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.AreaRepository;
import com.ticket.repositories.jpa.CategoriaTicketRepository;
import com.ticket.repositories.jpa.EstadoTicketRepository;
import com.ticket.repositories.jpa.HistorialTicketRepository;
import com.ticket.repositories.jpa.PrioridadTicketRepository;
import com.ticket.repositories.jpa.SlaRepository;
import com.ticket.repositories.jpa.TicketRepository;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.services.interfaces.AuditoriaService;
import com.ticket.services.interfaces.NotificacionService;
import com.ticket.services.interfaces.TicketService;
import com.ticket.services.support.FlujoEstados;

import jakarta.transaction.Transactional;

/**
 * Implementación del servicio de negocio de tickets.
 *
 * <p>Contiene la automatización central del proyecto (ver diagrama de secuencia
 * del diseño): al registrarse un ticket se valida la información, se calcula el
 * SLA, se asigna automáticamente un técnico y se deja traza en el historial; al
 * actualizarse su estado se valida el flujo del ciclo de vida y se notifica al
 * usuario.</p>
 */
@Service
public class TicketServiceImpl implements TicketService {

    /** Identificador del rol Técnico (coincide con el orden del DataSeeder y el frontend). */
    private static final long ROL_TECNICO_ID = 2L;

    /** Nivel de prioridad asignado cuando el solicitante no especifica uno. */
    private static final String PRIORIDAD_POR_DEFECTO = "MEDIA";

    /** Horas de resolución por defecto si no existe una regla de SLA configurada. */
    private static final int SLA_HORAS_POR_DEFECTO = 24;

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final CategoriaTicketRepository categoriaRepository;
    private final PrioridadTicketRepository prioridadRepository;
    private final EstadoTicketRepository estadoRepository;
    private final SlaRepository slaRepository;
    private final HistorialTicketRepository historialRepository;
    private final NotificacionService notificacionService;
    private final AuditoriaService auditoriaService;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             UsuarioRepository usuarioRepository,
                             AreaRepository areaRepository,
                             CategoriaTicketRepository categoriaRepository,
                             PrioridadTicketRepository prioridadRepository,
                             EstadoTicketRepository estadoRepository,
                             SlaRepository slaRepository,
                             HistorialTicketRepository historialRepository,
                             NotificacionService notificacionService,
                             AuditoriaService auditoriaService) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
        this.estadoRepository = estadoRepository;
        this.slaRepository = slaRepository;
        this.historialRepository = historialRepository;
        this.notificacionService = notificacionService;
        this.auditoriaService = auditoriaService;
    }

    // ─────────────────────────── Registro ───────────────────────────────────

    @Override
    @Transactional
    public TicketResponseDTO crear(CreateTicketDTO dto) {
        // 1. Resolver y validar las entidades referenciadas.
        UsuarioEntity solicitante = usuarioRepository.findById(dto.getIdUsuarioSolicitante())
                .orElseThrow(() -> CustomError.badRequest(
                        "El usuario solicitante no existe", "TicketServiceImpl", "idUsuarioSolicitante"));

        CategoriaTicketEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> CustomError.badRequest(
                        "La categoría indicada no existe", "TicketServiceImpl", "idCategoria"));

        PrioridadTicketEntity prioridad = resolverPrioridad(dto.getIdPrioridad());
        EstadoTicketEntity estadoAbierto = buscarEstado(FlujoEstados.ABIERTO);

        // 2. Construir el ticket (nace ABIERTO).
        LocalDateTime ahora = LocalDateTime.now();
        TicketEntity ticket = new TicketEntity();
        ticket.setUsuarioSolicitante(solicitante);
        ticket.setCategoria(categoria);
        ticket.setPrioridad(prioridad);
        ticket.setEstado(estadoAbierto);
        ticket.setTitulo(dto.getTitulo().trim());
        ticket.setDescripcion(dto.getDescripcion().trim());
        ticket.setFechaCreacion(ahora);
        ticket.setArea(resolverArea(dto.getIdArea(), solicitante));

        // 3. Cálculo automático del SLA según categoría + prioridad.
        ticket.setSlaVencimiento(calcularVencimientoSla(categoria, prioridad, ahora));

        // 4. Asignación automática de un técnico disponible (balanceo de carga).
        //    Si hay técnico, el ticket pasa de ABIERTO a ASIGNADO.
        UsuarioEntity tecnico = asignarTecnicoAutomatico();
        ticket.setUsuarioTecnico(tecnico);
        if (tecnico != null) {
            ticket.setEstado(buscarEstado(FlujoEstados.ASIGNADO));
        }

        TicketEntity guardado = ticketRepository.save(ticket);

        // 5. Trazabilidad: historial de creación y, si aplica, de asignación.
        registrarHistorial(guardado, solicitante, null, estadoAbierto,
                "CREACION", "Ticket registrado por el usuario solicitante.");
        if (tecnico != null) {
            registrarHistorial(guardado, tecnico, estadoAbierto, guardado.getEstado(), "ASIGNACION",
                    "Asignado automáticamente a " + nombreCompleto(tecnico) + ".");
        }

        // 6. Notificación de confirmación al solicitante.
        notificacionService.notificar(guardado, solicitante, "CREACION_TICKET",
                "Ticket #" + guardado.getIdTicket() + " registrado",
                "Su ticket \"" + guardado.getTitulo() + "\" fue registrado correctamente.");

        // 7. Auditoría.
        auditar(solicitante, AuditoriaService.CREAR_TICKET, guardado.getIdTicket(),
                "Registró el ticket: " + guardado.getTitulo());

        return TicketMapper.toTicketDTO(guardado);
    }

    // ─────────────────────────── Consulta ───────────────────────────────────

    @Override
    public TicketResponseDTO obtener(Long id) {
        return TicketMapper.toTicketDTO(buscarTicket(id));
    }

    @Override
    public PaginatedResponse<TicketResponseDTO> listar(String estado, Long idTecnico, Long idSolicitante,
                                                       Long idPrioridad, Long idArea, int page, int perPage) {
        // Selección del conjunto base según el filtro principal recibido.
        List<TicketEntity> base;
        if (idTecnico != null) {
            base = ticketRepository.findByUsuarioTecnico_IdUsuarioOrderByFechaCreacionDesc(idTecnico);
        } else if (idSolicitante != null) {
            base = ticketRepository.findByUsuarioSolicitante_IdUsuarioOrderByFechaCreacionDesc(idSolicitante);
        } else if (estado != null && !estado.isBlank()) {
            base = ticketRepository.findByEstado_CodigoOrderByFechaCreacionDesc(estado);
        } else {
            base = ticketRepository.findAllByOrderByFechaCreacionDesc();
        }

        // Filtros secundarios combinables (estado, prioridad y área).
        List<TicketEntity> filtrados = base.stream()
                .filter(t -> estado == null || estado.isBlank()
                        || (t.getEstado() != null && estado.equals(t.getEstado().getCodigo())))
                .filter(t -> idPrioridad == null
                        || (t.getPrioridad() != null && idPrioridad.equals(t.getPrioridad().getIdPrioridad())))
                .filter(t -> idArea == null
                        || (t.getArea() != null && idArea.equals(t.getArea().getIdArea())))
                .toList();

        // Paginación en memoria (suficiente para el volumen de la mesa de ayuda).
        long total = filtrados.size();
        int pagina = Math.max(page, 1);
        int tam = perPage <= 0 ? (int) Math.max(total, 1) : perPage;
        int desde = Math.min((pagina - 1) * tam, filtrados.size());
        int hasta = Math.min(desde + tam, filtrados.size());

        List<TicketResponseDTO> data = filtrados.subList(desde, hasta).stream()
                .map(TicketMapper::toTicketDTO)
                .toList();

        return new PaginatedResponse<>(data, total, pagina, tam);
    }

    // ─────────────────────────── Transiciones ───────────────────────────────

    @Override
    @Transactional
    public TicketResponseDTO atender(Long id, Long idTecnico) {
        TicketEntity ticket = buscarTicket(id);
        validarTransicion(ticket, FlujoEstados.EN_PROCESO);

        // Si llega un técnico y el ticket no tenía uno, se asigna en este punto.
        UsuarioEntity responsable = resolverResponsable(idTecnico, ticket.getUsuarioTecnico());
        if (ticket.getUsuarioTecnico() == null && responsable != null) {
            ticket.setUsuarioTecnico(responsable);
        }

        EstadoTicketEntity anterior = ticket.getEstado();
        ticket.setEstado(buscarEstado(FlujoEstados.EN_PROCESO));
        if (ticket.getFechaAtencion() == null) {
            ticket.setFechaAtencion(LocalDateTime.now());
        }
        TicketEntity guardado = ticketRepository.save(ticket);

        registrarHistorial(guardado, responsable, anterior, guardado.getEstado(),
                "ATENCION", "El técnico ha comenzado a atender el ticket.");
        notificarCambioEstado(guardado, "su ticket está siendo atendido");
        auditar(responsable, AuditoriaService.CAMBIO_ESTADO, guardado.getIdTicket(),
                "Inició la atención (EN_PROCESO)");

        return TicketMapper.toTicketDTO(guardado);
    }

    @Override
    @Transactional
    public TicketResponseDTO cerrar(Long id, AccionTicketDTO dto) {
        TicketEntity ticket = buscarTicket(id);

        if (ticket.getEstado() != null && Boolean.TRUE.equals(ticket.getEstado().getEsTerminal())) {
            throw CustomError.unprocessable(
                    "El ticket ya se encuentra en un estado terminal", "TicketServiceImpl",
                    ticket.getEstado().getCodigo());
        }

        UsuarioEntity responsable = resolverResponsable(
                dto != null ? dto.getIdUsuario() : null, ticket.getUsuarioTecnico());
        LocalDateTime ahora = LocalDateTime.now();
        EstadoTicketEntity anterior = ticket.getEstado();

        // Si el ticket aún estaba ABIERTO, se registra implícitamente su atención
        // para respetar el flujo antes de pasar a RESUELTO.
        if (ticket.getFechaAtencion() == null) {
            ticket.setFechaAtencion(ahora);
        }
        ticket.setEstado(buscarEstado(FlujoEstados.RESUELTO));
        ticket.setFechaResolucion(ahora);
        TicketEntity guardado = ticketRepository.save(ticket);

        String comentario = (dto != null && dto.getComentario() != null && !dto.getComentario().isBlank())
                ? dto.getComentario().trim()
                : "Ticket resuelto.";
        registrarHistorial(guardado, responsable, anterior, guardado.getEstado(), "RESOLUCION", comentario);
        notificarCambioEstado(guardado, "su ticket ha sido resuelto");
        auditar(responsable, AuditoriaService.CAMBIO_ESTADO, guardado.getIdTicket(),
                "Resolvió el ticket");

        return TicketMapper.toTicketDTO(guardado);
    }

    @Override
    @Transactional
    public TicketResponseDTO cambiarEstado(Long id, CambiarEstadoTicketDTO dto) {
        TicketEntity ticket = buscarTicket(id);
        String destino = dto.getCodigo() == null ? "" : dto.getCodigo().trim().toUpperCase();

        validarTransicion(ticket, destino);
        EstadoTicketEntity estadoDestino = buscarEstado(destino);
        EstadoTicketEntity anterior = ticket.getEstado();

        // Sellado de fechas según la naturaleza del nuevo estado.
        LocalDateTime ahora = LocalDateTime.now();
        if (FlujoEstados.EN_PROCESO.equals(destino) && ticket.getFechaAtencion() == null) {
            ticket.setFechaAtencion(ahora);
        }
        if (Boolean.TRUE.equals(estadoDestino.getEsTerminal()) && ticket.getFechaResolucion() == null) {
            ticket.setFechaResolucion(ahora);
        }
        ticket.setEstado(estadoDestino);

        UsuarioEntity responsable = resolverResponsable(dto.getIdUsuario(), ticket.getUsuarioTecnico());
        TicketEntity guardado = ticketRepository.save(ticket);

        String comentario = (dto.getComentario() != null && !dto.getComentario().isBlank())
                ? dto.getComentario().trim()
                : "Cambio de estado a " + estadoDestino.getNombre() + ".";
        registrarHistorial(guardado, responsable, anterior, estadoDestino, "CAMBIO_ESTADO", comentario);
        notificarCambioEstado(guardado, "el estado de su ticket cambió a " + estadoDestino.getNombre());
        auditar(responsable, AuditoriaService.CAMBIO_ESTADO, guardado.getIdTicket(),
                "Cambió el estado a " + estadoDestino.getNombre());

        return TicketMapper.toTicketDTO(guardado);
    }

    @Override
    @Transactional
    public TicketResponseDTO reasignarTecnico(Long id, Long idTecnico) {
        TicketEntity ticket = buscarTicket(id);
        UsuarioEntity tecnico = usuarioRepository.findById(idTecnico)
                .orElseThrow(() -> CustomError.badRequest(
                        "El técnico indicado no existe", "TicketServiceImpl", "idTecnico"));

        UsuarioEntity anterior = ticket.getUsuarioTecnico();
        ticket.setUsuarioTecnico(tecnico);
        // Si el ticket aún estaba ABIERTO, pasa a ASIGNADO al recibir técnico.
        if (ticket.getEstado() != null && FlujoEstados.ABIERTO.equals(ticket.getEstado().getCodigo())) {
            ticket.setEstado(buscarEstado(FlujoEstados.ASIGNADO));
        }
        TicketEntity guardado = ticketRepository.save(ticket);

        String detalle = "Reasignado a " + nombreCompleto(tecnico)
                + (anterior != null ? " (antes: " + nombreCompleto(anterior) + ")" : "");
        registrarHistorial(guardado, tecnico, null, null, "REASIGNACION", detalle);
        notificacionService.notificar(guardado, tecnico, "ASIGNACION",
                "Ticket #" + id + " asignado", "Se te ha asignado el ticket: " + guardado.getTitulo());
        auditoriaService.registrarActorActual(AuditoriaService.REASIGNACION, "Ticket #" + id, detalle);

        return TicketMapper.toTicketDTO(guardado);
    }

    @Override
    @Transactional
    public TicketResponseDTO cambiarPrioridad(Long id, Long idPrioridad) {
        TicketEntity ticket = buscarTicket(id);
        PrioridadTicketEntity prioridad = prioridadRepository.findById(idPrioridad)
                .orElseThrow(() -> CustomError.badRequest(
                        "La prioridad indicada no existe", "TicketServiceImpl", "idPrioridad"));

        String anterior = ticket.getPrioridad() != null ? ticket.getPrioridad().getNivel() : "—";
        ticket.setPrioridad(prioridad);
        // Recalcular el vencimiento de SLA con la nueva prioridad.
        if (ticket.getFechaCreacion() != null) {
            ticket.setSlaVencimiento(
                    calcularVencimientoSla(ticket.getCategoria(), prioridad, ticket.getFechaCreacion()));
        }
        TicketEntity guardado = ticketRepository.save(ticket);

        String detalle = "Prioridad cambiada de " + anterior + " a " + prioridad.getNivel();
        registrarHistorial(guardado, ticket.getUsuarioTecnico(), null, null, "CAMBIO_PRIORIDAD", detalle);
        auditoriaService.registrarActorActual(AuditoriaService.CAMBIO_PRIORIDAD, "Ticket #" + id, detalle);

        return TicketMapper.toTicketDTO(guardado);
    }

    // ─────────────────────────── Historial ──────────────────────────────────

    @Override
    public List<HistorialTicketDTO> historial(Long idTicket) {
        buscarTicket(idTicket); // valida existencia
        return historialRepository.findByTicket_IdTicketOrderByFechaCambioAsc(idTicket).stream()
                .map(TicketMapper::toHistorialDTO)
                .toList();
    }

    @Override
    @Transactional
    public HistorialTicketDTO agregarComentario(Long idTicket, AccionTicketDTO dto) {
        TicketEntity ticket = buscarTicket(idTicket);
        if (dto == null || dto.getComentario() == null || dto.getComentario().isBlank()) {
            throw CustomError.badRequest("El comentario es obligatorio", "TicketServiceImpl", "comentario");
        }
        UsuarioEntity autor = resolverResponsable(dto.getIdUsuario(), ticket.getUsuarioSolicitante());
        String tipo = (dto.getTipoEvento() != null && !dto.getTipoEvento().isBlank())
                ? dto.getTipoEvento().trim()
                : "COMENTARIO";

        HistorialTicketEntity h = registrarHistorial(ticket, autor, null, null, tipo, dto.getComentario().trim());
        return TicketMapper.toHistorialDTO(h);
    }

    // ─────────────────────── Helpers de negocio ─────────────────────────────

    /**
     * Resuelve la prioridad a partir del id recibido o, si es nulo, devuelve la
     * prioridad por defecto del sistema.
     */
    private PrioridadTicketEntity resolverPrioridad(Long idPrioridad) {
        if (idPrioridad != null) {
            return prioridadRepository.findById(idPrioridad)
                    .orElseThrow(() -> CustomError.badRequest(
                            "La prioridad indicada no existe", "TicketServiceImpl", "idPrioridad"));
        }
        return prioridadRepository.findByNivel(PRIORIDAD_POR_DEFECTO)
                .orElseGet(() -> prioridadRepository.findAll().stream().findFirst().orElse(null));
    }

    /**
     * Resuelve el área del ticket: usa el id indicado o, en su defecto, el área del
     * usuario solicitante.
     */
    private AreaEntity resolverArea(Long idArea, UsuarioEntity solicitante) {
        if (idArea != null) {
            return areaRepository.findById(idArea)
                    .orElseThrow(() -> CustomError.badRequest(
                            "El área indicada no existe", "TicketServiceImpl", "idArea"));
        }
        return solicitante != null ? solicitante.getArea() : null;
    }

    /**
     * Calcula la fecha límite de resolución (SLA) sumando, a la fecha de creación,
     * las horas de resolución de la regla aplicable. Busca primero una regla
     * específica de categoría + prioridad; si no existe, una de la prioridad; y,
     * en último caso, aplica un valor por defecto.
     */
    private LocalDateTime calcularVencimientoSla(CategoriaTicketEntity categoria,
                                                 PrioridadTicketEntity prioridad,
                                                 LocalDateTime desde) {
        int horas = SLA_HORAS_POR_DEFECTO;
        if (categoria != null && prioridad != null) {
            SlaEntity regla = slaRepository
                    .findByCategoria_IdCategoriaAndPrioridad_IdPrioridad(
                            categoria.getIdCategoria(), prioridad.getIdPrioridad())
                    .orElseGet(() -> slaRepository.findByPrioridad_IdPrioridad(prioridad.getIdPrioridad())
                            .stream().findFirst().orElse(null));
            if (regla != null && regla.getTiempoResolucion() != null) {
                horas = regla.getTiempoResolucion();
            }
        }
        return desde.plusHours(horas);
    }

    /**
     * Selecciona automáticamente el técnico activo con menor carga de tickets
     * abiertos (balanceo simple). Si no hay técnicos disponibles, el ticket queda
     * sin asignar para su distribución manual posterior.
     */
    private UsuarioEntity asignarTecnicoAutomatico() {
        List<UsuarioEntity> tecnicos = usuarioRepository.findByRol_IdRolAndActivoTrue(ROL_TECNICO_ID);
        return tecnicos.stream()
                .min(Comparator.comparingLong(t ->
                        ticketRepository.countByUsuarioTecnico_IdUsuarioAndEstado_EsTerminalFalse(t.getIdUsuario())))
                .orElse(null);
    }

    /**
     * Valida que el ticket pueda transitar a {@code destino}; lanza un error 422
     * con un mensaje claro si la transición no respeta el ciclo de vida.
     */
    private void validarTransicion(TicketEntity ticket, String destino) {
        String origen = ticket.getEstado() != null ? ticket.getEstado().getCodigo() : null;
        if (!FlujoEstados.esTransicionValida(origen, destino)) {
            throw CustomError.unprocessable(
                    "Transición de estado no permitida: " + origen + " → " + destino,
                    "TicketServiceImpl", "flujo-estado");
        }
    }

    /**
     * Crea y persiste un evento de historial (bitácora de auditoría del ticket).
     *
     * @return el evento de historial creado.
     */
    private HistorialTicketEntity registrarHistorial(TicketEntity ticket, UsuarioEntity usuario,
                                                     EstadoTicketEntity anterior, EstadoTicketEntity nuevo,
                                                     String tipoEvento, String comentario) {
        HistorialTicketEntity h = new HistorialTicketEntity();
        h.setTicket(ticket);
        h.setUsuario(usuario);
        h.setEstadoAnterior(anterior);
        h.setEstadoNuevo(nuevo);
        h.setTipoEvento(tipoEvento);
        h.setComentario(comentario);
        h.setFechaCambio(LocalDateTime.now());
        return historialRepository.save(h);
    }

    /** Registra un evento de auditoría asociado a un ticket. */
    private void auditar(UsuarioEntity actor, String accion, Long idTicket, String detalle) {
        auditoriaService.registrar(
                actor != null ? actor.getIdUsuario() : null,
                actor != null ? actor.getEmail() : null,
                accion, "Ticket #" + idTicket, detalle);
    }

    /** Envía una notificación de cambio de estado al solicitante del ticket. */
    private void notificarCambioEstado(TicketEntity ticket, String mensaje) {
        notificacionService.notificar(ticket, ticket.getUsuarioSolicitante(), "CAMBIO_ESTADO",
                "Actualización del ticket #" + ticket.getIdTicket(),
                "Hola, " + mensaje + ".");
    }

    /** Busca un ticket por id o lanza 404 si no existe. */
    private TicketEntity buscarTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "TicketServiceImpl"));
    }

    /** Busca un estado por su código o lanza 404 si el catálogo no lo contiene. */
    private EstadoTicketEntity buscarEstado(String codigo) {
        return estadoRepository.findByCodigo(codigo)
                .orElseThrow(() -> CustomError.internalServer(
                        "Estado no configurado: " + codigo, "TicketServiceImpl", "catalogo-estado"));
    }

    /**
     * Determina el usuario responsable de una acción: usa el id recibido si está
     * presente; en su defecto, el usuario alternativo (técnico/solicitante).
     */
    private UsuarioEntity resolverResponsable(Long idUsuario, UsuarioEntity alternativo) {
        if (idUsuario != null) {
            return usuarioRepository.findById(idUsuario).orElse(alternativo);
        }
        return alternativo;
    }

    /** Nombre completo legible de un usuario para mensajes de historial. */
    private String nombreCompleto(UsuarioEntity u) {
        return (u.getNombre() == null ? "" : u.getNombre())
                + " " + (u.getApellido() == null ? "" : u.getApellido());
    }
}
