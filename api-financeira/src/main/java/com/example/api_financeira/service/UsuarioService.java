package com.example.api_financeira.service;

import com.example.api_financeira.domain.Conta;
import com.example.api_financeira.domain.Usuario;
import com.example.api_financeira.domain.repository.ContaRepository;
import com.example.api_financeira.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.UUID;

// A anotação @Service avisa o Spring que esta é uma classe de regra de negócio
@Service
public class UsuarioService {

    // Injeção de dependências: O Service precisa dos Repositories para salvar no banco
    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, ContaRepository contaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // @Transactional é o segredo de APIs financeiras!
    // Se der erro ao criar a Conta, ele desfaz a criação do Usuário. Tudo ou nada.
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        
        // CRIPTOGRAFA A SENHA ANTES DE SALVAR!
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Conta novaConta = new Conta();
        novaConta.setNumero(UUID.randomUUID().toString().substring(0, 8)); 
        novaConta.setSaldo(BigDecimal.ZERO);
        novaConta.setUsuario(usuarioSalvo); 

        contaRepository.save(novaConta);

        return usuarioSalvo;
    }
}
