package com.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la API REST del Sistema de Tickets de SERPOST.
 *
 * <p>Arranca el contenedor de Spring Boot que expone los servicios web bajo el
 * contexto {@code /api}. La carga de datos iniciales se delega a
 * {@link com.ticket.config.DataSeeder} para mantener esta clase libre de lógica.</p>
 *
 * @author Equipo SGT-SERPOST
 */
@SpringBootApplication
public class TicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

}
