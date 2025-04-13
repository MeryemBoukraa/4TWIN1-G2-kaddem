package com.example.apigetway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class ApiGetWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGetWayApplication.class, args);
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("Contrat", r -> r.path("/Contrat/**")
						.uri("lb://CONTRAT"))
				.route("universite", r -> r.path("/universite/**")
						.uri("lb://UNIVERSITE"))
				.route("Ressource", r -> r.path("/Ressource/**")
						.uri("lb://RESSOURCE"))
				.route("departementMicroService", r -> r.path("/departementMicroService/**")
						.uri("lb://DEPARTEMENTMICROSERVICE"))
				.route("formation", r -> r.path("/formation/**")
						.uri("lb://FORMATION"))
				.route("equipe", r -> r.path("/equipe/**")
						.filters(f -> f.rewritePath("/equipe/(?<segment>.*)", "/Kassil/equipe/${segment}"))
						.uri("lb://EQUIPE"))



				.build();
	}
}
