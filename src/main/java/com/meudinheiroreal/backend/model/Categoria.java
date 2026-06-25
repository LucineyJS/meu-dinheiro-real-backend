package com.meudinheiroreal.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 10)
    private String tipo;

    @Column(length = 50)
    private String icone;

    @Column(name = "data_categoria", nullable = false, updatable = false)
    private LocalDateTime dataCategoria;

    @Column(name = "data_alteracao_categoria")
    private LocalDateTime dataAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        dataCategoria = LocalDateTime.now();
        dataAlteracao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAlteracao = LocalDateTime.now();
    }
    public Categoria(String nome, String tipo, String icone, Usuario idusuario) {
        this.nome = nome;
        this.tipo = tipo;
        this.icone = icone;
        this.usuario = idusuario;
    }
}
