package com.meudinheiroreal.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "categoria")
@Getter
@Setter
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

    @Column(name = "data_categoria", insertable = false,updatable = false)
    private LocalDateTime dataCategoria;

    @Column(name = "data_alteracao_categoria", insertable = false,updatable = false)
    private LocalDateTime dataAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
