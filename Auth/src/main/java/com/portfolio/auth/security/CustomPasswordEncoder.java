package com.portfolio.auth.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		//Hash mdp en fonction du facteur de coût qui est 10 par défaut (le temps necessaire de hashage)
		return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
	}

}
