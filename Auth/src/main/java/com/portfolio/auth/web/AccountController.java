package com.portfolio.auth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.auth.dtos.LoginRequestDto;
import com.portfolio.auth.dtos.LoginResponseDto;
import com.portfolio.auth.dtos.RegisterRequestDto;
import com.portfolio.auth.dtos.RegisterResponseDto;
import com.portfolio.auth.entites.User;
import com.portfolio.auth.services.IAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permet les requêtes depuis d'autres origines
public class AccountController {
	
	public final IAccountService accountService;
	
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
		return accountService.register(request.getUser());
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
		return accountService.login(loginRequest.getEmail(), loginRequest.getPassword());		
	}
	
	@GetMapping("/account?email={email}")
	public UserDetails loadUserByUsername(@RequestParam String email) {
		return accountService.loadUserByUsername(email);
	}
	
	@GetMapping("/nice")
	public String nice() {
		return "Nice Job Arafet!";
	}
}
