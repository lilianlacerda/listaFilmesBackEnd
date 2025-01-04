package br.com.listadefilmes.projeto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.listadefilmes.projeto.domain.filme.Filme;
import br.com.listadefilmes.projeto.service.FilmeService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:5500")
@RequestMapping("filme")
public class FilmeController {
	
	private FilmeService filmeService;
	
	 public FilmeController(FilmeService filmeService) {
		 this.filmeService = filmeService;
	}

	@GetMapping("/listar")
	public ResponseEntity<List<Filme>> listarFilme(@RequestParam(value = "query", required = false, defaultValue = "") String query){
		 List<Filme> filmes;
		 if(query.isEmpty()){
			 // Se nenhum paâmetro de pesquisa for enviado, retorna todos os filmes
			 filmes = filmeService.listarFilmes();
		 }else{
			 //Se um termo de pesquisa foi enviado, busca os filmes correspondentes
			 filmes = filmeService.buscarFilmes(query.trim().toLowerCase());
		 }
		 return ResponseEntity.status(200).body(filmes);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Filme> criarFilme(@Valid @RequestBody Filme filme){
		return ResponseEntity.status(201).body(filmeService.criarFilme(filme));
	}

	//TODO: Revisar a edição de filmes
	@PutMapping("/editar")
	public ResponseEntity<Filme> editarFilme(@Valid @RequestBody Filme filme){
		return ResponseEntity.status(200).body(filmeService.editarFilme(filme));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirFilme(@PathVariable Integer id){
		filmeService.excluirFilme(id);
		return ResponseEntity.status(204).build();
	}
	
}
