package com.meudinheiroreal.backend.repository;

import com.meudinheiroreal.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByUsuarioIdUsuario(Long idUsuario );

    Optional<Categoria> findByIdCategoriaAndUsuarioIdUsuario(Long idCategoria, Long idUsuario);
}
