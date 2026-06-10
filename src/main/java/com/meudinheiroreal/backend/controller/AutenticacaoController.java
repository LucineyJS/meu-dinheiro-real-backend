package com.meudinheiroreal.backend.controller;


import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.UsuarioRepository;
import com.meudinheiroreal.backend.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Email já cadastrado!");
        }
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        usuario.setDataUsuario(LocalDateTime.now());
        usuario.setDataAlteracaoUsuario(LocalDateTime.now());
        usuario.setStatus(1);
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario dadosLogin) {
        try {

                var autenticacaoToken = new UsernamePasswordAuthenticationToken(dadosLogin.getEmail(), dadosLogin.getSenhaHash());
                Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken);
                String tokenJWT = tokenService.gerarToken(autenticacao.getName());
                return ResponseEntity.ok(Map.of("token", tokenJWT));
            } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Email ou senha Errado");
        }
    }
}