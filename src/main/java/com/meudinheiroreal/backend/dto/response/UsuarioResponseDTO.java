package com.meudinheiroreal.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioResponseDTO {
    private  Long idUsuario;
    private  String nome;
    private  String email;
    private LocalDateTime dataUsuario;
}