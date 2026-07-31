package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.dto.request.UsuarioRequestDTO;
import com.meudinheiroreal.backend.dto.response.UsuarioResponseDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.model.enums.StatusUsuario;
import com.meudinheiroreal.backend.model.enums.TipoCategoria;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import com.meudinheiroreal.backend.repository.UsuarioRepository;
import com.meudinheiroreal.backend.utils.TextoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        // 1. Valida se o e-mail já existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Erro: Email já cadastrado!");
        }

        // 2. Mapeia o DTO para a Entidade e criptografa a senha
        Usuario usuario = new Usuario();
        usuario.setNome(TextoUtils.formatarTexto(dto.getNome()));
        usuario.setEmail(dto.getEmail().toLowerCase().trim());
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario.setDataUsuario(LocalDateTime.now());
        usuario.setDataAlteracaoUsuario(LocalDateTime.now());
        usuario.setStatus(StatusUsuario.ATIVO);

        // 3. Salva o usuário no banco
        Usuario salvo = usuarioRepository.save(usuario);

        // 4. Cria as categorias padrão inicial
        List<Categoria> categoriasPadrao = List.of(
                new Categoria("Salário", TipoCategoria.RECEITA, "dollar-sign", salvo),
                new Categoria("Investimentos", TipoCategoria.RECEITA, "chart-line", salvo),
                new Categoria("Trabalho", TipoCategoria.RECEITA, "briefcase", salvo),
                new Categoria("Moradia", TipoCategoria.DESPESA, "home", salvo),
                new Categoria("Alimentação", TipoCategoria.DESPESA, "shopping-cart", salvo),
                new Categoria("Transporte", TipoCategoria.DESPESA, "car", salvo),
                new Categoria("Lazer", TipoCategoria.DESPESA, "gamepad", salvo),
                new Categoria("Saúde", TipoCategoria.DESPESA, "medkit", salvo),
                new Categoria("Educação", TipoCategoria.DESPESA, "graduation-cap", salvo)
        );
        categoriaRepository.saveAll(categoriasPadrao);

        // 5. Monta o DTO de resposta
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setIdUsuario(salvo.getIdUsuario());
        responseDTO.setNome(salvo.getNome());
        responseDTO.setEmail(salvo.getEmail());
        responseDTO.setDataUsuario(salvo.getDataUsuario());

        return responseDTO;
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("Erro: Usuário não encontrado!"));

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setIdUsuario(usuario.getIdUsuario());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setDataUsuario(usuario.getDataUsuario());

        return responseDTO;
    }
}