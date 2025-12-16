package com.portfolio.auth.services;

import org.springframework.http.ResponseEntity;

import com.portfolio.auth.dtos.LoginResponseDto;
import com.portfolio.auth.dtos.RegisterResponseDto;
import com.portfolio.auth.entites.User;

public interface IAccountService {

	ResponseEntity<RegisterResponseDto>  register(User user);
	
	ResponseEntity<LoginResponseDto>  login(String email, String password);
	
}
