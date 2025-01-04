package br.com.listadefilmes.projeto.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
	
}
