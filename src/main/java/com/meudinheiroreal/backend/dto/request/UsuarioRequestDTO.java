package com.meudinheiroreal.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private  String nome;
    private  String email;
    private  String senhaHash;
}