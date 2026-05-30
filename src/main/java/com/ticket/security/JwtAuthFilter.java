package com.ticket.security;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que autentica cada petición a partir del JWT del encabezado
 * {@code Authorization: Bearer <token>}.
 *
 * <p>Si el token es válido, coloca en el contexto de seguridad un usuario
 * autenticado con la autoridad correspondiente a su rol ({@code ROLE_<ROL>}). Si no
 * hay token o es inválido, no autentica y deja que las reglas de autorización
 * decidan (los endpoints protegidos responderán 401).</p>
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HEADER);
        if (header != null && header.startsWith(PREFIX)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = header.substring(PREFIX.length()).trim();
            try {
                Claims claims = jwtService.parsear(token);
                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);

                var authorities = rol == null
                        ? List.<SimpleGrantedAuthority>of()
                        : List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));

                var authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                // Token inválido o expirado: se ignora y la petición continúa sin
                // autenticación (el endpoint protegido devolverá 401).
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
