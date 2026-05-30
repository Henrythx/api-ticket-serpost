package com.ticket.repositories.jpa;

import com.ticket.model.usuario.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
}
