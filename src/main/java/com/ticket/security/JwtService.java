package com.ticket.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio de emisión y verificación de JSON Web Tokens (JWT).
 *
 * <p>Implementa el pilar de Autenticación del diseño de seguridad: tras validar las
 * credenciales, se emite un token firmado (HS256) que contiene el identificador y
 * el rol del usuario y un tiempo de expiración. En cada petición posterior el token
 * se verifica para confirmar que no fue alterado y que sigue vigente.</p>
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-ms}") long expirationMs) {
        // La clave debe tener al menos 256 bits (32 bytes) para HS256.
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Genera un token firmado para el usuario autenticado.
     *
     * @param email      correo del usuario (se usa como "subject").
     * @param idUsuario  identificador del usuario.
     * @param idRol      identificador del rol.
     * @param nombreRol  nombre del rol (usado para las autoridades de Spring Security).
     * @return token JWT compacto (cadena).
     */
    public String generarToken(String email, Long idUsuario, Long idRol, String nombreRol) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("id", idUsuario)
                .claim("idRol", idRol)
                .claim("rol", nombreRol)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusMillis(expirationMs)))
                .signWith(key)
                .compact();
    }

    /**
     * Verifica la firma y la vigencia del token y devuelve sus claims.
     *
     * @param token token JWT (sin el prefijo "Bearer ").
     * @return claims contenidos en el token.
     * @throws io.jsonwebtoken.JwtException si el token es inválido o está expirado.
     */
    public Claims parsear(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
