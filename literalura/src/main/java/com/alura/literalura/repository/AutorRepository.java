package com.alura.literalura.repository;

import com.alura.literalura.service.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);

    // Busca autores que nasceram antes ou no ano E que morreram depois ou estão vivos
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL)")
    List<Autor> buscarAutoresVivosNoAno(Integer ano);
}
