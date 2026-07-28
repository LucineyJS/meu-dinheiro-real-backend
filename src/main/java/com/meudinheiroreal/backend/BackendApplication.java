package com.meudinheiroreal.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication // SecurityAutoConfiguration ativado para carregar o SecurityConfig.java
public class BackendApplication {

	@PostConstruct
	public void init() {
		// Define o fuso horário padrão do sistema para São Paulo/Brasil
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}