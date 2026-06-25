package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.UsuarioRequestDTO;
import com.meudinheiroreal.backend.dto.response.UsuarioResponseDTO;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.UsuarioRepository;
import com.meudinheiroreal.backend.security.TokenService;
import com.meudinheiroreal.backend.service.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Email já cadastrado!");
        }
        Usuario novoUsuario =new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenhaHash(passwordEncoder.encode(dto.getSenhaHash()));

        UsuarioResponseDTO responseDTO = usuarioService.registrarUsuario(novoUsuario) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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