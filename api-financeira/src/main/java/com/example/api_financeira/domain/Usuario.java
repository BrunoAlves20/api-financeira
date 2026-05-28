package com.example.api_financeira.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails { // A MÁGICA ACONTECE AQUI
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String senha;

    // --- MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY ---

    // Define o perfil de acesso (neste caso, todo mundo é um usuário comum)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // O Spring pergunta: "Qual é o campo de login?" Nós respondemos: O E-mail.
    @Override
    public String getUsername() {
        return email;
    }

    // O Spring pergunta: "Qual é o campo de senha?"
    @Override
    public String getPassword() {
        return senha;
    }

    // As 4 validações abaixo dizem que a conta não está bloqueada ou expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}