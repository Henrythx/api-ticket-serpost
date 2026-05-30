package com.ticket.repositories.ticket;

import com.ticket.model.ticket.SlaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SlaRepository extends JpaRepository<SlaEntity, Long> {
    Optional<SlaEntity> findByCategoria_IdCategoriaAndPrioridad_IdPrioridad(Long idCategoria, Long idPrioridad);
}
