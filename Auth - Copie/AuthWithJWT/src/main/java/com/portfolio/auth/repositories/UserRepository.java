package com.portfolio.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.auth.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	//Optional, cette méthode peut ne rien renvoi si User n'existe pas 
	Optional<User> findByEmail(String email);
	
}
