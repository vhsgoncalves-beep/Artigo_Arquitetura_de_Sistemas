package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe principal de inicializacao da aplicacao Spring Boot.
 *
 * <p>O sistema de Biblioteca foi desenvolvido como estudo de caso academico
 * para demonstrar a aplicacao pratica dos cinco principios SOLID e dos
 * padroes de projeto GoF (Factory Method, Facade e Observer).</p>
 */
@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

    /**
     * Configuracao simples de CORS para permitir que o frontend
     * (React + Vite, porta 5173) consuma a API REST do backend.
     */
    @org.springframework.context.annotation.Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
