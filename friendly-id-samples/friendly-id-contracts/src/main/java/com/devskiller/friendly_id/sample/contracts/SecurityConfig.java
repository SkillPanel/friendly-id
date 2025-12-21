package com.devskiller.friendly_id.sample.contracts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/**").authenticated()
						.requestMatchers(org.springframework.http.HttpMethod.POST, "/items").authenticated()
						.anyRequest().permitAll()
				)
				.csrf(csrf -> csrf.disable())
				.httpBasic(basic -> {})
				.build();
	}
}
