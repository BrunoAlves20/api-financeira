package com.example.api_financeira.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Esse método diz pro Spring Security: "Por enquanto, libere todos os caminhos. 
    // Só queremos usar o seu criptografador de senhas".
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                // .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) -> Adicione isso se quiser ser 100% RESTful
                .authorizeHttpRequests(auth -> auth
                        // Libera o cadastro de usuário e o login para qualquer um
                        .requestMatchers("/api/usuarios").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        // Bloqueia TODAS as outras rotas (transações, extratos, etc)
                        .anyRequest().authenticated()
                )
                // Avisa pro Spring rodar o nosso filtro ANTES do filtro padrão dele
                .addFilterBefore(securityFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Esse método ensina o Spring a usar o algoritmo BCrypt para hashear senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Adicione este método dentro da classe SecurityConfig
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}