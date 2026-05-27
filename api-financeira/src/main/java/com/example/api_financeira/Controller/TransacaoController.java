package com.example.api_financeira.Controller;

import com.example.api_financeira.domain.Transacao;
import com.example.api_financeira.dto.TransacaoDTO;
import com.example.api_financeira.dto.TransferenciaDTO;
import com.example.api_financeira.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/deposito")
    public ResponseEntity<Transacao> realizarDeposito(@RequestBody TransacaoDTO dto) {
        Transacao transacao = transacaoService.depositar(dto.numeroConta(), dto.valor());
        return ResponseEntity.ok(transacao);
    }

    @PostMapping("/saque")
    public ResponseEntity<Transacao> realizarSaque(@RequestBody TransacaoDTO dto) {
        Transacao transacao = transacaoService.sacar(dto.numeroConta(), dto.valor());
        return ResponseEntity.ok(transacao);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transacao> realizarTransferencia(@RequestBody TransferenciaDTO dto) {
        Transacao transacao = transacaoService.transferir(
                dto.numeroContaOrigem(), 
                dto.numeroContaDestino(), 
                dto.valor()
        );
        return ResponseEntity.ok(transacao);
    }

    // O {numeroConta} na URL vira uma variável no Java através do @PathVariable
    @GetMapping("/extrato/{numeroConta}")
    public ResponseEntity<java.util.List<Transacao>> consultarExtrato(@PathVariable String numeroConta) {
        
        java.util.List<Transacao> extrato = transacaoService.tirarExtrato(numeroConta);
        return ResponseEntity.ok(extrato);
    }
}