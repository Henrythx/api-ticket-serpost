package com.ticket.services.impl.ticket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.dto.ticket.ticket.CreateTicketDTO;
import com.ticket.dto.ticket.ticket.TicketListDTO;
import com.ticket.dto.ticket.ticket.TicketResponseDTO;
import com.ticket.dto.ticket.ticket.UpdateTicketDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.model.ticket.HistorialTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.ticket.CategoriaTicketRepository;
import com.ticket.repositories.ticket.EstadoTicketRepository;
import com.ticket.repositories.ticket.HistorialTicketRepository;
import com.ticket.repositories.ticket.PrioridadTicketRepository;
import com.ticket.repositories.ticket.TicketRepository;
import com.ticket.repositories.usuario.UsuarioRepository;
import com.ticket.services.interfaces.TicketService;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoTicketRepository estadoRepository;

    @Autowired
    private PrioridadTicketRepository prioridadRepository;

    @Autowired
    private CategoriaTicketRepository categoriaRepository;

    @Autowired
    private HistorialTicketRepository historialRepository;

    @Transactional
    @Override
    public TicketResponseDTO crearTicket(CreateTicketDTO dto) {
        UsuarioEntity solicitante = usuarioRepository.findById(dto.getIdUsuarioSolicitante())
                .orElseThrow(() -> CustomError.notFound("Usuario solicitante no encontrado", "TicketServiceImpl"));

        EstadoTicketEntity estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "TicketServiceImpl"));

        PrioridadTicketEntity prioridad = prioridadRepository.findById(dto.getIdPrioridad())
                .orElseThrow(() -> CustomError.notFound("Prioridad no encontrada", "TicketServiceImpl"));

        CategoriaTicketEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> CustomError.notFound("Categoría no encontrada", "TicketServiceImpl"));

        TicketEntity entity = new TicketEntity();
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUsuarioSolicitante(solicitante);
        entity.setEstado(estado);
        entity.setPrioridad(prioridad);
        entity.setCategoria(categoria);
        entity.setFechaCreacion(LocalDateTime.now());

        if (dto.getIdUsuarioTecnico() != null) {
            UsuarioEntity tecnico = usuarioRepository.findById(dto.getIdUsuarioTecnico())
                    .orElseThrow(() -> CustomError.notFound("Usuario técnico no encontrado", "TicketServiceImpl"));
            entity.setUsuarioTecnico(tecnico);
        }

        TicketEntity saved = ticketRepository.save(entity);
        return TicketResponseDTO.fromEntity(saved);
    }

    @Transactional
    @Override
    public TicketResponseDTO actualizarTicket(Long idTicket, UpdateTicketDTO dto) {
        TicketEntity entity = ticketRepository.findById(idTicket)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "TicketServiceImpl"));

        if (dto.getTitulo() != null) entity.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getIdUsuarioTecnico() != null) {
            UsuarioEntity tecnico = usuarioRepository.findById(dto.getIdUsuarioTecnico())
                    .orElseThrow(() -> CustomError.notFound("Usuario técnico no encontrado", "TicketServiceImpl"));
            entity.setUsuarioTecnico(tecnico);
        }
        if (dto.getIdCategoria() != null) {
            CategoriaTicketEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> CustomError.notFound("Categoría no encontrada", "TicketServiceImpl"));
            entity.setCategoria(categoria);
        }
        if (dto.getIdPrioridad() != null) {
            PrioridadTicketEntity prioridad = prioridadRepository.findById(dto.getIdPrioridad())
                    .orElseThrow(() -> CustomError.notFound("Prioridad no encontrada", "TicketServiceImpl"));
            entity.setPrioridad(prioridad);
        }
        if (dto.getIdEstado() != null) {
            EstadoTicketEntity estado = estadoRepository.findById(dto.getIdEstado())
                    .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "TicketServiceImpl"));
            entity.setEstado(estado);
        }

        TicketEntity updated = ticketRepository.save(entity);
        return TicketResponseDTO.fromEntity(updated);
    }

    @Transactional
    @Override
    public void cambiarEstado(Long idTicket, Long idEstado) {
        TicketEntity entity = ticketRepository.findById(idTicket)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "TicketServiceImpl"));

        EstadoTicketEntity nuevoEstado = estadoRepository.findById(idEstado)
                .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "TicketServiceImpl"));

        EstadoTicketEntity anterior = entity.getEstado();
        entity.setEstado(nuevoEstado);

        ticketRepository.save(entity);

        HistorialTicketEntity historial = new HistorialTicketEntity();
        historial.setTicket(entity);
        historial.setUsuario(entity.getUsuarioTecnico());
        historial.setEstadoAnterior(anterior);
        historial.setEstadoNuevo(nuevoEstado);
        historial.setTipoEvento("Cambio de estado");
        historial.setFechaCambio(LocalDateTime.now());

        historialRepository.save(historial);
    }

    @Transactional(readOnly = true)
    @Override
    public TicketResponseDTO obtenerTicketPorId(Long idTicket) {
        TicketEntity entity = ticketRepository.findById(idTicket)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "TicketServiceImpl"));
        return TicketResponseDTO.fromEntity(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TicketListDTO> listarTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketListDTO::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarTicket(Long idTicket) {
        TicketEntity entity = ticketRepository.findById(idTicket)
                .orElseThrow(() -> CustomError.notFound("Ticket no encontrado", "TicketServiceImpl"));
        ticketRepository.delete(entity);
    }
}

