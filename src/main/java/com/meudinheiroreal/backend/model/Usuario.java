package com.meudinheiroreal.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "data_usuario", nullable = false)
    private LocalDateTime dataUsuario;

    @Column(name = "data_alteracao_usuario", nullable = false)
    private LocalDateTime dataAlteracaoUsuario;

    @Column(nullable = false)
    private Integer status;
}