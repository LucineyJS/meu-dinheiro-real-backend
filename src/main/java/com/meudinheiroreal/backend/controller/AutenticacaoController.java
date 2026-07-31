package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.LoginRequestDTO;
import com.meudinheiroreal.backend.dto.request.UsuarioRequestDTO;
import com.meudinheiroreal.backend.dto.response.LoginResponseDTO;
import com.meudinheiroreal.backend.dto.response.UsuarioResponseDTO;
import com.meudinheiroreal.backend.security.TokenService;
import com.meudinheiroreal.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO responseDTO = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dadosLogin) {
        try {
            var autenticacaoToken = new UsernamePasswordAuthenticationToken(dadosLogin.getEmail(), dadosLogin.getSenha());
            Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken);

            // Gera o token JWT
            String tokenJWT = tokenService.gerarToken(autenticacao.getName());

            // Busca os dados do usuário para preencher o DTO de resposta
            UsuarioResponseDTO usuarioDTO = usuarioService.buscarPorEmail(dadosLogin.getEmail());

            // Retorna o token e o objeto do usuário encapsulados no LoginResponseDTO
            LoginResponseDTO responseDTO = new LoginResponseDTO(tokenJWT, usuarioDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Email ou senha incorretos");
        }
    }
}