package com.example.api_financeira.service;

import com.example.api_financeira.domain.Conta;
import com.example.api_financeira.domain.Transacao;
import com.example.api_financeira.domain.repository.ContaRepository;
import com.example.api_financeira.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public Transacao depositar(String numeroConta, java.math.BigDecimal valor) {
        // 1. Busca a conta no banco. Se não achar, "estoura" um erro.
        Conta conta = contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        // 2. Adiciona o valor ao saldo atual
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        // 3. Registra o extrato da transação
        Transacao transacao = new Transacao();
        transacao.setContaDestino(conta); // Depósito só tem destino
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setTipo("DEPOSITO");

        return transacaoRepository.save(transacao);
    }

    @Transactional
    public Transacao sacar(String numeroConta, java.math.BigDecimal valor) {
        Conta conta = contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        // Regra de Ouro: Bloqueia saque se o valor for maior que o saldo
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente para realizar o saque!");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(conta); // Saque só tem origem
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setTipo("SAQUE");

        return transacaoRepository.save(transacao);
    }

    @Transactional
    public Transacao transferir(String contaOrigemId, String contaDestinoId, java.math.BigDecimal valor) {
        
        // 1. Busca as duas contas no banco
        Conta origem = contaRepository.findByNumero(contaOrigemId)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada!"));
                
        Conta destino = contaRepository.findByNumero(contaDestinoId)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada!"));

        // 2. Verifica se a origem tem dinheiro suficiente
        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente para transferência!");
        }

        // 3. Faz a matemática (tira de uma, bota na outra)
        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        // 4. Salva as contas atualizadas
        contaRepository.save(origem);
        contaRepository.save(destino);

        // 5. Gera o comprovante da transação
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(origem);
        transacao.setContaDestino(destino);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setTipo("TRANSFERENCIA");

        return transacaoRepository.save(transacao);
    }

    public java.util.List<Transacao> tirarExtrato(String numeroConta) {
        
        // Verifica se a conta existe antes de buscar o extrato
        contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        return transacaoRepository.emitirExtrato(numeroConta);
    }

}

