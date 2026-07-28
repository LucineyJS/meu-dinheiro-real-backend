package com.meudinheiroreal.backend.dto.request;


import com.meudinheiroreal.backend.model.enums.TipoLancamento;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LancamentoRequestDTO {

    @NotNull(message = "O Valor é obrigatório")
    @Positive(message ="O Valor deve ser maior que zero!")
    private BigDecimal valor;

    @Size(max = 255, message = "A Descrição deve ter no maximo 255 caracteres!")
    private String descricao;

    @NotNull(message = "O Tipo é obrigatório!")
    private TipoLancamento tipo;

    @NotNull(message = "O ID da Categoria é obrigatório!")
    private Long idCategoria;
}