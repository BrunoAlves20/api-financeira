package com.example.api_financeira.Controller;

import com.example.api_financeira.domain.Usuario;
import com.example.api_financeira.dto.DadosAutenticacaoDTO;
import com.example.api_financeira.dto.TokenDTO;
import com.example.api_financeira.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> efetuarLogin(@RequestBody DadosAutenticacaoDTO dados) {
        // 1. Empacota o e-mail e a senha que vieram da requisição
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        
        // 2. O gerente do Spring Security verifica se a senha está correta (ele compara com o hash do banco)
        var authentication = manager.authenticate(authenticationToken);
        
        // 3. Se passou pela linha de cima, a senha está certa! Geramos o Token.
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        
        // 4. Devolvemos o token na resposta
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}