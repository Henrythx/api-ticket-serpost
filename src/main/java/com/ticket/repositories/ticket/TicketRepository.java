package com.ticket.repositories.ticket;

import com.ticket.model.ticket.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByUsuarioSolicitante_IdUsuario(Long idUsuario);
    List<TicketEntity> findByUsuarioTecnico_IdUsuario(Long idUsuario);
    List<TicketEntity> findByCategoria_IdCategoria(Long idCategoria);
    List<TicketEntity> findByPrioridad_IdPrioridad(Long idPrioridad);
    List<TicketEntity> findByEstado_IdEstado(Long idEstado);
}
