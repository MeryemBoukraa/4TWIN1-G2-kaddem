package com.example.apigetway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGetWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGetWayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes (RouteLocatorBuilder builder) {
		return builder.routes()
				.route("Contrat", r -> r.path("/Contrat/**")
						.uri("lb://CONTRAT"))//candidat:8056
				.route("universite", r -> r.path("/universite/**")
						.uri("lb://UNIVERSITE"))//job:8081

				.route("Ressource", r -> r.path("/Ressource/**")
						.uri("lb://RESSOURCE"))//job:8081

				.route("departementMicroService", r -> r.path("/departementMicroService/**")
						.uri("lb://DEPARTEMENTMICROSERVICE"))
				.route("formation", r -> r.path("/formation/**")
						.uri("lb://formation"))
				.route("equipe", r -> r.path("/equipe/**")
						.uri("lb://equipe"))




				.build();
	}
}
