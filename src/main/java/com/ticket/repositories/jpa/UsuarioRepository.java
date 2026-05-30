package com.ticket.repositories.jpa;

import com.ticket.model.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByNombreIgnoreCase(String nombre);
    List<UsuarioEntity> findByArea_IdArea(Long idArea);
    List<UsuarioEntity> findByRol_IdRol(Long idRol);
    List<UsuarioEntity> findByNombreContainingOrApellidoContaining(String nombre, String apellido);

    /**
     * Técnicos (rol indicado) activos de un área, usados por la asignación
     * automática de tickets. Se ordenan para favorecer un reparto estable.
     *
     * @param idArea área de soporte.
     * @param idRol  identificador del rol técnico.
     * @return técnicos activos del área.
     */
    List<UsuarioEntity> findByArea_IdAreaAndRol_IdRolAndActivoTrue(Long idArea, Long idRol);

    /**
     * Todos los usuarios activos con un rol determinado (respaldo de asignación
     * cuando un área no tiene técnicos propios).
     *
     * @param idRol identificador del rol.
     * @return usuarios activos con ese rol.
     */
    List<UsuarioEntity> findByRol_IdRolAndActivoTrue(Long idRol);
}