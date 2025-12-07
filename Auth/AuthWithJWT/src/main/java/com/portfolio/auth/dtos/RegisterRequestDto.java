package com.portfolio.auth.dtos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.portfolio.auth.entites.User;
import com.portfolio.auth.web.AccountController;

import lombok.Data;

@Data
public class RegisterRequestDto {

	private User user;

	public User getUser() {
		return user;
	}

	@PostMapping("")
	public ResponseEntity<RegisterResponseDto> register(AccountController accountController) {
		return accountController.accountService.register(getUser());
	}

}