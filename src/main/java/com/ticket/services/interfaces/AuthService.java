package com.ticket.services.interfaces;

import com.ticket.dto.auth.LoginDTO;
import com.ticket.dto.auth.SesionUsuarioDTO;

/**
 * Servicio de utilidad de autenticación.
 *
 * <p>Es un servicio transversal y reutilizable por cualquier módulo: valida
 * credenciales y produce la información de sesión del usuario. Constituye la base
 * sobre la que, en una fase posterior, se emitirá un token JWT.</p>
 */
public interface AuthService {

    /**
     * Valida las credenciales recibidas y devuelve la sesión del usuario.
     *
     * @param dto credenciales (identificador y contraseña).
     * @return datos de sesión del usuario autenticado.
     * @throws com.ticket.model.CustomError 401 si las credenciales son inválidas
     *                                       o la cuenta está inactiva.
     */
    SesionUsuarioDTO login(LoginDTO dto);
}
