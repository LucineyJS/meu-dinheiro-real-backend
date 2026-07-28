package com.meudinheiroreal.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origem permitida (Seu front-end Angular)
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // Cabeçalhos permitidos
        configuration.setAllowedHeaders(List.of("*"));

        // Cabeçalhos expostos para o Angular conseguir ler o token no header se preciso
        configuration.setExposedHeaders(List.of("Authorization"));

        // Permite envio de credenciais/cookies
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}