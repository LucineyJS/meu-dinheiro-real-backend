package com.meudinheiroreal.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LancamentoResponseDTO {
    private Long idLancamento;
    private BigDecimal valor;
    private String descricao;
    private String tipo;
    private LocalDateTime dataLancamento;
    private LocalDateTime dataAlteracao;
    private String nomeCategoria;
    private long idCategoria;
}