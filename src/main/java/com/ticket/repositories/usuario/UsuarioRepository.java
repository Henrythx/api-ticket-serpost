package com.ticket.repositories.usuario;

import com.ticket.model.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    Optional<UsuarioEntity> findByEmail(String email);
    List<UsuarioEntity> findByArea_IdArea(Long idArea);
    List<UsuarioEntity> findByRol_IdRol(Long idRol);
    List<UsuarioEntity> findByNombreContainingOrApellidoContaining(String nombre, String apellido);
}