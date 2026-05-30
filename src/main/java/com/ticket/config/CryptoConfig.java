package com.ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de cifrado.
 *
 * <p>Expone un {@link PasswordEncoder} basado en BCrypt, usado por el servicio de
 * autenticación para almacenar y verificar contraseñas de forma segura, tal como
 * se exige en el pilar de Confidencialidad del diseño del servicio web.</p>
 */
@Configuration
public class CryptoConfig {

    /**
     * Codificador de contraseñas BCrypt (factor de trabajo por defecto = 10).
     *
     * @return instancia única de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
