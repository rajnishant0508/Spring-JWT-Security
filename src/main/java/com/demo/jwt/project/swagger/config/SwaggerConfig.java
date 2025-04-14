package com.demo.jwt.project.swagger.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		
        Server httpsServer = new Server();
        httpsServer.setUrl("https://spring-jwt-security-production.up.railway.app"); // <-- Change this to your real domain

		return new OpenAPI()
				.info(new Info().title("Spring Security JWT Authentication Service"))
				.addSecurityItem(new SecurityRequirement().addList("JavaSecurityScheme"))
				.components(new Components().addSecuritySchemes("JavaSecurityScheme",
						new SecurityScheme()
						.name("JavaSecurityScheme")
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")))
				.servers(List.of(httpsServer));

	}
}
