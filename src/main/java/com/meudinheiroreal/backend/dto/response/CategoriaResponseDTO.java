package com.meudinheiroreal.backend.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoriaResponseDTO {
    private Long IdCategoria;
    private String nome;
    private String tipo;
    private String icone;
    private LocalDateTime dataCategoria;
    private LocalDateTime dataAlteracao;
}