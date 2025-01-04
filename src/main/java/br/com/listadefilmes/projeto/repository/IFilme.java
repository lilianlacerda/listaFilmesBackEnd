package br.com.listadefilmes.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.listadefilmes.projeto.domain.filme.Filme;

import java.util.List;

public interface IFilme extends JpaRepository<Filme, Long>{
    List<Filme> findByNomeContainingIgnoreCaseOrSinopseContainingIgnoreCase(String nome, String sinopse);
}
