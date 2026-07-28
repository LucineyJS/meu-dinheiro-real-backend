package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.response.ResumoFinanceiroDTO;
import com.meudinheiroreal.backend.model.Usuario; // Importação necessária
import com.meudinheiroreal.backend.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder; // Importação necessária
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    // 1. Método para resgatar o usuário logado pelo token de segurança
    private Usuario getUsuarioLogado() {
        var autenticacao = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) autenticacao.getPrincipal();
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoFinanceiroDTO> obterResumo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        // 2. Pega o ID do usuário logado
        Long idUsuario = getUsuarioLogado().getIdUsuario();

        // 3. Passa o ID para o service
        ResumoFinanceiroDTO resumo = relatorioService.gerarResumo(inicio, fim, idUsuario);
        return ResponseEntity.ok(resumo);
    }
}