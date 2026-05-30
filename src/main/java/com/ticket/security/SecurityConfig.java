package com.ticket.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuración de seguridad de la API basada en JWT.
 *
 * <p>Define una cadena de filtros sin estado (sin sesiones de servidor): cada
 * petición se autentica exclusivamente con el token. Reglas de acceso:</p>
 * <ul>
 *   <li>Públicos: {@code /auth/**} (login) y {@code /health}.</li>
 *   <li>{@code /dashboard/**}: solo rol Administrador (autorización por rol).</li>
 *   <li>El resto: requiere un token válido (cualquier rol autenticado).</li>
 * </ul>
 *
 * <p>El CORS se integra aquí (vía {@link CorsConfigurationSource}) para que aplique
 * dentro de la cadena de Spring Security.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // API REST stateless: no se usa CSRF ni sesiones de servidor.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // El preflight CORS nunca lleva token.
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Documentacion OpenAPI/Swagger UI.
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Endpoints públicos (incluye el alias de login).
                        .requestMatchers("/auth/**", "/health", "/usuarios/login").permitAll()
                        // Autorización por rol: dashboard y auditoría son exclusivos
                        // del administrador.
                        .requestMatchers("/dashboard/**", "/auditoria/**").hasRole("ADMINISTRADOR")
                        // Gestion administrativa: solo administrador.
                        .requestMatchers(
                                "/usuarios", "/usuarios/**",
                                "/roles", "/roles/**",
                                "/areas", "/areas/**",
                                "/sla", "/sla/**")
                        .hasRole("ADMINISTRADOR")
                        // Acciones operativas del ticket: solo técnico o administrador
                        // (el cliente únicamente crea, consulta y comenta sus tickets).
                        .requestMatchers(HttpMethod.POST, "/tickets/*/atender", "/tickets/*/cerrar")
                                .hasAnyRole("ADMINISTRADOR", "TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/tickets/*").hasAnyRole("ADMINISTRADOR", "TECNICO")
                        .requestMatchers(HttpMethod.PATCH, "/tickets/**").hasAnyRole("ADMINISTRADOR", "TECNICO")
                        // Todo lo demás requiere autenticación con token válido.
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(unauthorizedEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                // Valida el JWT antes del filtro estándar de usuario/contraseña.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** Política CORS para que el frontend (otro origen) consuma la API con token. */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /** Respuesta 401 (no autenticado) en formato JSON, consistente con la API. */
    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, ex) ->
                escribirJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "No autorizado: token ausente o inválido");
    }

    /** Respuesta 403 (autenticado pero sin permisos) en formato JSON. */
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) ->
                escribirJson(response, HttpServletResponse.SC_FORBIDDEN,
                        "Acceso denegado: permisos insuficientes para este recurso");
    }

    private static void escribirJson(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"status\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
