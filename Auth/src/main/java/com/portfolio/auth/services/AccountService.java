package com.portfolio.auth.services;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.auth.dtos.LoginResponseDto;
import com.portfolio.auth.dtos.RegisterResponseDto;
import com.portfolio.auth.entites.User;
import com.portfolio.auth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService,UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Lazy
	private final AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<RegisterResponseDto> register(User userRequest) {
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent())
			// message d'erreur
			return ResponseEntity.badRequest().body(RegisterResponseDto.builder()
					.message("Un utilisateur existe déja avec cette adresse émail").build());
		else {
			var user = User.builder().email(userRequest.getEmail()).firstname(userRequest.getFirstname())
					.lastname(userRequest.getLastname()).password(passwordEncoder.encode(userRequest.getPassword()))
					.build();
			userRepository.save(user);
			return ResponseEntity
					.ok(RegisterResponseDto.builder().message("Utilisateur enregistré avec succès!").build());
		}
	}

	@Override
	public ResponseEntity<LoginResponseDto> login(String email, String password) {
		// déléger a spring security
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		
		// c'est une maniere de faire, sinon on peut remplacer avec Spring Security AuthetificationManager
		try {
			// Rechercher l'utilisateur par email
			Optional<User> userOptional = userRepository.findByEmail(email);

			User user = userOptional.isPresent() ? userOptional.get() : null;
			// Vérifier le mot de passe (en production, utiliser un encodage BCrypt)
			if (user != null && user.getPassword().equals(password)) {
				// Authentification réussie
				LoginResponseDto response = new LoginResponseDto(user.getId(), user.getFirstname(), user.getLastname(),
						user.getEmail(), "Authentification réussie");
				return ResponseEntity.ok(response);

			} else {
				// Authentification échouée
				LoginResponseDto response = new LoginResponseDto();
				response.setMessage("Email ou mot de passe incorrect");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

		} catch (Exception e) {
			// Erreur serveur
			LoginResponseDto response = new LoginResponseDto();
			response.setMessage("Erreur lors de l'authentification: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Cette utilisateur n'existe pas!"));
	}

	@Override
	public ResponseEntity<LoginResponseDto> getAccountById(String id) {
		return null;
	}
}
