package com.portfolio.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity //Activer Spring security
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filerChain(HttpSecurity httpSecurity) throws Exception {
		//Désactive les attaque CSRF (Cross-Site Request Forgery)
		httpSecurity.csrf(csrf -> csrf.disable())
		//TODO: Désactivier temporairement la sécurité sur l'iframe a destination des attaques Clickjacking
		.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
		.authorizeHttpRequests(request -> request.requestMatchers(toH2Console()).permitAll())
		.authorizeHttpRequests(request -> request.requestMatchers("api/v1/account/**").permitAll())
		//Par defaut on est sur du STATFULL
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
		return authenticationConfig.getAuthenticationManager();
	}

}
