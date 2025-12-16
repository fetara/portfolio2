package com.portfolio.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.auth.entites.User;
import com.portfolio.auth.repositories.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Vérifier si la base est vide
            if (userRepository.count() == 0) {
                // Créer un utilisateur de test
                User testUser = User.builder()
                    .firstname("Arafet")
                    .lastname("Test")
                    .email("arafet@test.com")
                    .password("password123")
                    .build();
                
                userRepository.save(testUser);
                
                System.out.println("✅ Utilisateur de test créé :");
                System.out.println("   Email: arafet@test.com");
                System.out.println("   Password: password123");
                
                // Créer un deuxième utilisateur de test
                User testUser2 = User.builder()
                    .firstname("John")
                    .lastname("Doe")
                    .email("john@test.com")
                    .password("test123")
                    .build();
                
                userRepository.save(testUser2);
                
                System.out.println("✅ Deuxième utilisateur de test créé :");
                System.out.println("   Email: john@test.com");
                System.out.println("   Password: test123");
            }
        };
    }
}
