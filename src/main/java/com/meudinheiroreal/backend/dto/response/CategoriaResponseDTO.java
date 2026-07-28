package com.meudinheiroreal.backend.dto.response;

import com.meudinheiroreal.backend.model.enums.TipoCategoria;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoriaResponseDTO {
    private Long idCategoria;
    private String nome;
    private TipoCategoria tipo;
    private String icone;
    private LocalDateTime dataCategoria;
    private LocalDateTime dataAlteracao;
}