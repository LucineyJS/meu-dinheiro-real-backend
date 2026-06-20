package com.meudinheiroreal.backend.repository;

import com.meudinheiroreal.backend.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findByUsuarioIdUsuarioOrderByDataLancamentoDesc(Long idUsuario );

    Optional<Lancamento> findByIdLancamentoAndUsuarioIdUsuario(Long idLancamento, Long idUsuario);
}