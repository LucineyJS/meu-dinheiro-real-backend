package com.meudinheiroreal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
@NoArgsConstructor
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    private Long idLancamento;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal valor;

    private String descricao;

    @Column(nullable = false, length = 10)
    private String tipo;

    @Column(name = "data_lancamento", nullable = false, updatable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "data_alteracao_lancamento")
    private LocalDateTime dataAlteracao;

    @Column(nullable = false, length = 20)
    private String status = "EFETIVADO";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @PrePersist
    protected void aoCriar() {
        this.dataLancamento = LocalDateTime.now();
        this.dataAlteracao = LocalDateTime.now();
    }

    @PreUpdate
    protected void aoAtualizar() {
        this.dataAlteracao = LocalDateTime.now();
    }
}