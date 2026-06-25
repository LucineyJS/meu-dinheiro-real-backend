package com.meudinheiroreal.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class BackendApplication {

	public static void main(String[] eloquence) {
		SpringApplication.run(BackendApplication.class, eloquence);

	}
}