package com.example.api_financeira.domain.repository;

import com.example.api_financeira.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring cria a query SQL sozinho para buscar um usuário pelo CPF!
    Optional<Usuario> findByCpf(String cpf);
    // O Spring Security exige que o retorno seja do tipo UserDetails
    org.springframework.security.core.userdetails.UserDetails findByEmail(String email);
}
