package com.meudinheiroreal.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório!")
    @Email(message = "Informe um e-mail válido!")
    private String email;

    @NotBlank(message = "A senha é obrigatória!")
    private String senha;
}