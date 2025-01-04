package br.com.listadefilmes.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.listadefilmes.projeto.domain.user.AuthenticationDTO;
import br.com.listadefilmes.projeto.domain.user.LoginResponseDTO;
import br.com.listadefilmes.projeto.domain.user.RegisterDTO;
import br.com.listadefilmes.projeto.domain.user.User;
import br.com.listadefilmes.projeto.infra.security.TokenService;
import br.com.listadefilmes.projeto.repository.IUserRepository;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:5500")
@RequestMapping("projeto")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
			var projeto = this.authenticationManager.authenticate(usernamePassword);

			var token = tokenService.generateToken((User) projeto.getPrincipal());

			return ResponseEntity.ok(new LoginResponseDTO(token));
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Credenciais inválidas");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
		if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().body("Usuário já existe");
		
		String encryptedPassword = passwordEncoder.encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, data.role());
		
		this.repository.save(newUser);
		return ResponseEntity.ok("Usuário Salvo com sucesso!");
	}
}
