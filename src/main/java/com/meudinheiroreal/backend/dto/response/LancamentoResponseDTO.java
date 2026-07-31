package com.meudinheiroreal.backend.dto.response;

import com.meudinheiroreal.backend.model.enums.TipoLancamento;
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
    private TipoLancamento tipo;
    private LocalDateTime dataLancamento;
    private LocalDateTime dataAlteracao;
    private String nomeCategoria;
    private Long idCategoria;
    private String iconeCategoria;
}