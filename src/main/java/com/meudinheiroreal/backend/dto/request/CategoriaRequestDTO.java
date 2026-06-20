package com.meudinheiroreal.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequestDTO {

    @NotBlank(message = "O Nome da categoria é obrigatório!")
    @Size(max = 50, message = "O nome deve ter no maximo 50 caracteres!")
    private String nome;

    @NotBlank(message = "O Tipo é obrigatório!")
    @Pattern(regexp = "RECEITA|DESPESA", message = "O Tipo deve ser RECEITA ou DESPESA!")
    private String tipo;

    @Size(max = 50, message = "O Ícone deve ter no maximo 50 caracteres!")
    private String icone;
}