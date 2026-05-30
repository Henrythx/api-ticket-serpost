package com.ticket.services.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ticket.dto.dashboard.CargaTecnicoDTO;
import com.ticket.dto.dashboard.ConteoEstadoDTO;
import com.ticket.dto.dashboard.ConteoPrioridadDTO;
import com.ticket.dto.dashboard.DashboardDTO;
import com.ticket.dto.dashboard.RankingUsuarioDTO;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.TicketRepository;
import com.ticket.services.interfaces.DashboardService;
import com.ticket.services.support.FlujoEstados;

/**
 * Implementación del servicio analítico del dashboard.
 *
 * <p>Recorre el conjunto de tickets una sola vez para derivar todos los KPIs
 * (conteos por estado y prioridad, vencidos, SLA, tiempos y rankings),
 * manteniendo la lógica de cálculo en la capa de negocio.</p>
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    /** Tamaño máximo de los rankings de técnicos y clientes. */
    private static final int TOP_N = 5;

    private final TicketRepository ticketRepository;

    public DashboardServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public DashboardDTO obtenerResumen() {
        List<TicketEntity> tickets = ticketRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        DashboardDTO dto = new DashboardDTO();
        dto.setTotal(tickets.size());

        long sumaAtencionMin = 0, conAtencion = 0;
        long sumaResolucionMin = 0, conResolucion = 0;
        long resueltosConSla = 0, resueltosEnPlazo = 0;
        long terminados = 0, vencidos = 0;

        Map<String, ConteoEstadoDTO> porEstado = new LinkedHashMap<>();
        Map<String, ConteoPrioridadDTO> porPrioridad = new LinkedHashMap<>();
        Map<Long, CargaTecnicoDTO> cargaTecnicos = new LinkedHashMap<>();
        Map<Long, RankingUsuarioDTO> topTecnicos = new LinkedHashMap<>();
        Map<Long, RankingUsuarioDTO> topClientes = new LinkedHashMap<>();

        for (TicketEntity t : tickets) {
            EstadoTicketEntity estado = t.getEstado();
            String codigo = estado != null ? estado.getCodigo() : "SIN_ESTADO";
            String nombre = estado != null ? estado.getNombre() : "Sin estado";
            boolean terminal = estado != null && Boolean.TRUE.equals(estado.getEsTerminal());

            // Conteo por estado.
            porEstado.computeIfAbsent(codigo, c -> new ConteoEstadoDTO(c, nombre, 0));
            incrementarEstado(porEstado.get(codigo));

            // Conteos por categoría del ciclo de vida.
            switch (codigo) {
                case FlujoEstados.ABIERTO -> dto.setAbiertos(dto.getAbiertos() + 1);
                case FlujoEstados.ASIGNADO -> dto.setAsignados(dto.getAsignados() + 1);
                case FlujoEstados.EN_PROCESO -> dto.setEnProceso(dto.getEnProceso() + 1);
                case FlujoEstados.PENDIENTE -> dto.setPendientes(dto.getPendientes() + 1);
                case FlujoEstados.RESUELTO -> dto.setResueltos(dto.getResueltos() + 1);
                case FlujoEstados.CERRADO -> dto.setCerrados(dto.getCerrados() + 1);
                default -> { /* sin efecto */ }
            }

            // Conteo por prioridad.
            PrioridadTicketEntity pri = t.getPrioridad();
            if (pri != null) {
                porPrioridad.computeIfAbsent(pri.getNivel(),
                        n -> new ConteoPrioridadDTO(n, pri.getColorHex(), 0));
                incrementarPrioridad(porPrioridad.get(pri.getNivel()));
            }

            if (terminal) {
                terminados++;
            }
            if (!terminal && t.getSlaVencimiento() != null && t.getSlaVencimiento().isBefore(ahora)) {
                vencidos++;
            }

            // Tiempos de atención y resolución + cumplimiento de SLA.
            if (t.getFechaAtencion() != null && t.getFechaCreacion() != null) {
                sumaAtencionMin += ChronoUnit.MINUTES.between(t.getFechaCreacion(), t.getFechaAtencion());
                conAtencion++;
            }
            if (t.getFechaResolucion() != null && t.getFechaCreacion() != null) {
                sumaResolucionMin += ChronoUnit.MINUTES.between(t.getFechaCreacion(), t.getFechaResolucion());
                conResolucion++;
                if (t.getSlaVencimiento() != null) {
                    resueltosConSla++;
                    if (!t.getFechaResolucion().isAfter(t.getSlaVencimiento())) {
                        resueltosEnPlazo++;
                    }
                }
            }

            // Carga (tickets activos) y ranking de técnicos (incidencias resueltas).
            UsuarioEntity tecnico = t.getUsuarioTecnico();
            if (tecnico != null) {
                if (!terminal) {
                    cargaTecnicos.computeIfAbsent(tecnico.getIdUsuario(),
                            id -> new CargaTecnicoDTO(id, nombreUsuario(tecnico), 0));
                    CargaTecnicoDTO c = cargaTecnicos.get(tecnico.getIdUsuario());
                    c.setTicketsActivos(c.getTicketsActivos() + 1);
                }
                if (t.getFechaResolucion() != null) {
                    sumarRanking(topTecnicos, tecnico);
                }
            }

            // Ranking de clientes (más solicitudes registradas).
            UsuarioEntity cliente = t.getUsuarioSolicitante();
            if (cliente != null) {
                sumarRanking(topClientes, cliente);
            }
        }

        dto.setVencidos(vencidos);
        dto.setTiempoPromedioAtencionHoras(promedioHoras(sumaAtencionMin, conAtencion));
        dto.setTiempoPromedioResolucionHoras(promedioHoras(sumaResolucionMin, conResolucion));
        dto.setCumplimientoSlaPorcentaje(porcentaje(resueltosEnPlazo, resueltosConSla));
        dto.setTasaResolucionPorcentaje(porcentaje(terminados, tickets.size()));
        dto.setPorEstado(new ArrayList<>(porEstado.values()));
        dto.setPorPrioridad(new ArrayList<>(porPrioridad.values()));
        dto.setCargaTecnicos(new ArrayList<>(cargaTecnicos.values()));
        dto.setTopTecnicos(top(topTecnicos));
        dto.setTopClientes(top(topClientes));

        return dto;
    }

    // ─────────────────────────── Helpers ────────────────────────────────────

    private void incrementarEstado(ConteoEstadoDTO c) {
        c.setCantidad(c.getCantidad() + 1);
    }

    private void incrementarPrioridad(ConteoPrioridadDTO c) {
        c.setCantidad(c.getCantidad() + 1);
    }

    /** Acumula un ticket en el ranking del usuario indicado. */
    private void sumarRanking(Map<Long, RankingUsuarioDTO> ranking, UsuarioEntity usuario) {
        ranking.computeIfAbsent(usuario.getIdUsuario(),
                id -> new RankingUsuarioDTO(id, nombreUsuario(usuario), 0));
        RankingUsuarioDTO r = ranking.get(usuario.getIdUsuario());
        r.setCantidad(r.getCantidad() + 1);
    }

    /** Ordena un ranking de mayor a menor cantidad y devuelve el top N. */
    private List<RankingUsuarioDTO> top(Map<Long, RankingUsuarioDTO> ranking) {
        return ranking.values().stream()
                .sorted(Comparator.comparingLong(RankingUsuarioDTO::getCantidad).reversed())
                .limit(TOP_N)
                .toList();
    }

    private String nombreUsuario(UsuarioEntity u) {
        return ((u.getNombre() == null ? "" : u.getNombre()) + " "
                + (u.getApellido() == null ? "" : u.getApellido())).trim();
    }

    private double promedioHoras(long sumaMinutos, long cantidad) {
        if (cantidad == 0) {
            return 0d;
        }
        return Math.round((sumaMinutos / (double) cantidad) / 60d * 100d) / 100d;
    }

    private double porcentaje(long parte, long total) {
        if (total == 0) {
            return 0d;
        }
        return Math.round((parte * 100d / total) * 100d) / 100d;
    }
}
