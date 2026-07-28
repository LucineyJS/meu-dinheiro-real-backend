package com.meudinheiroreal.backend.dto.request;

import com.meudinheiroreal.backend.model.enums.TipoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "O Nome da categoria é obrigatório!")
    @Size(max = 50, message = "O nome deve ter no maximo 50 caracteres!")
    private String nome;

    @NotNull(message = "O Tipo é obrigatório(RECEITA OU DESPESA)!")
    private TipoCategoria tipo;

    @Size(max = 50, message = "O Ícone deve ter no maximo 50 caracteres!")
    private String icone;
}