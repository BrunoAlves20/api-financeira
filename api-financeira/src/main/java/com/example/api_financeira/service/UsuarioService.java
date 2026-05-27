package com.example.api_financeira.service;

import com.example.api_financeira.domain.Conta;
import com.example.api_financeira.domain.Usuario;
import com.example.api_financeira.domain.repository.ContaRepository;
import com.example.api_financeira.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

// A anotação @Service avisa o Spring que esta é uma classe de regra de negócio
@Service
public class UsuarioService {

    // Injeção de dependências: O Service precisa dos Repositories para salvar no banco
    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ContaRepository contaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
    }

    // @Transactional é o segredo de APIs financeiras!
    // Se der erro ao criar a Conta, ele desfaz a criação do Usuário. Tudo ou nada.
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        
        // 1. Salva o usuário no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // 2. Cria a conta bancária automaticamente
        Conta novaConta = new Conta();
        // Gera um número de conta aleatório de 8 caracteres (simulando um número de banco)
        novaConta.setNumero(UUID.randomUUID().toString().substring(0, 8)); 
        novaConta.setSaldo(BigDecimal.ZERO);
        novaConta.setUsuario(usuarioSalvo); // Vincula a conta ao usuário que acabou de nascer

        // 3. Salva a conta no banco de dados
        contaRepository.save(novaConta);

        return usuarioSalvo;
    }
}
