package br.com.listadefilmes.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.listadefilmes.projeto.domain.user.User;

public interface IUserRepository extends JpaRepository<User, String>{
	UserDetails findByLogin(String login);

}
