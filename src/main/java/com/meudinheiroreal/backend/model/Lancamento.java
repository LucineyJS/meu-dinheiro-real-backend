package com.meudinheiroreal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    private Long idLancamento;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal valor;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, length = 10)
    private String tipo;

    @Column(name = "data_lancamento", insertable = false,updatable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "data_alteracao_lancamento", insertable = false,updatable = false)
    private LocalDateTime dataAlteracao;

    @Column(nullable = false, length = 20)
    private String status = "EFETIVADO";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}