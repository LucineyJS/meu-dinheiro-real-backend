package com.meudinheiroreal.backend.dto.request;


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

    @NotBlank(message = "O Tipo é obrigatório!")
    @Pattern(regexp = "RECEITA|DESPESA", message = "O Tipo deve ser RECEITA ou DESPESA!")
    private String tipo;

    @NotNull(message = "A Data do Lançamento é obrigatória!")
    private LocalDateTime dataLancamento;

    private String status;

    @NotNull(message = "O ID da Categoria é obrigatório!")
    private Long idCategoria;
}