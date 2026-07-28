package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.model.enums.StatusUsuario;
import com.meudinheiroreal.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        if (usuario.getStatus() != null && usuario.getStatus() == StatusUsuario.INATIVO) {
            throw new UsernameNotFoundException("Este usuário está inativo!");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .roles("USER")
                .build();
    }
}