package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.dto.request.UsuarioRequestDTO;
import com.meudinheiroreal.backend.dto.response.UsuarioResponseDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import com.meudinheiroreal.backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public UsuarioResponseDTO registrarUsuario(Usuario usuario){
        usuario.setDataUsuario(LocalDateTime.now());
        usuario.setDataAlteracaoUsuario(LocalDateTime.now());
        usuario.setStatus(1);
        Usuario salvo = usuarioRepository.save(usuario);

        List<Categoria> categoriasPadrao = List.of(
                new Categoria("Salário", "RECEITA", "briefcase", salvo),
                new Categoria("Investimentos", "RECEITA", "home", salvo),
                new Categoria("Moradia", "DESPESA", "home",salvo),
                new Categoria("Alimentação", "DESPESA", "utensils", salvo),
                new Categoria("Transporte", "DESPESA", "car",salvo),
                new Categoria("Lazer", "DESPESA", "gamepad", salvo),
                new Categoria("Saúde", "DESPESA","heartbeat", salvo),
                new Categoria("Educação", "DESPESA", "graduation-cap", salvo)
        );
        categoriaRepository.saveAll(categoriasPadrao);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setIdUsuario(salvo.getIdUsuario());
        responseDTO.setNome(salvo.getNome());
        responseDTO.setEmail(salvo.getEmail());
        responseDTO.setDataUsuario(salvo.getDataUsuario());

        return responseDTO;
    }
}