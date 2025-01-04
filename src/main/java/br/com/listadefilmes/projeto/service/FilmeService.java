package br.com.listadefilmes.projeto.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.listadefilmes.projeto.domain.filme.Filme;
import br.com.listadefilmes.projeto.repository.IFilme;

@Repository
public class FilmeService {
	
	private IFilme repository;
	
	public FilmeService(IFilme repository) {
		this.repository = repository;
	}
	
	public List<Filme> listarFilmes(){
		List<Filme> lista = repository.findAll();
		return lista;
	}

	public List<Filme> buscarFilmes(String query) {
		if (query == null || query.trim().isEmpty()) {
			return repository.findAll(); // Retorna todos os filmes se não houver pesquisa
		}
			return repository.findByNomeContainingIgnoreCaseOrSinopseContainingIgnoreCase(query, query);
	}
	
	public Filme criarFilme(Filme filme) {
		Filme filmeNovo = repository.save(filme);
		return filmeNovo;
	}
	
	//TODO: Revisar essa edição de filme
	public Filme editarFilme(Filme filme) {
		Filme filmeNovo = repository.save(filme);
		return filmeNovo;
	}
	
	public boolean excluirFilme(Integer id) {
		repository.deleteById(Long.valueOf(id));
		return true;
	}

}
