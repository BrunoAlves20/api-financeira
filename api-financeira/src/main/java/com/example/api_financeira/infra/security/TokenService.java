package com.example.api_financeira.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.api_financeira.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // O Spring vai puxar aquela senha que você acabou de colocar no properties!
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de criptografia usando a nossa palavra secreta
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            
            return JWT.create()
                    .withIssuer("API Financeira") // Quem está emitindo o token?
                    .withSubject(usuario.getEmail()) // A quem pertence esse token?
                    .withExpiresAt(dataExpiracao()) // Quando ele perde a validade?
                    .sign(algoritmo); // Assina e finaliza
                    
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Regra de negócio: O token expira em 2 horas. 
    // Passado esse tempo, o usuário precisará fazer login novamente.
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Financeira")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}