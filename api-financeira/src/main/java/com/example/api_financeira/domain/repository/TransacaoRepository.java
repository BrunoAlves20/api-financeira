package com.example.api_financeira.domain.repository;

import com.example.api_financeira.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // JPQL: Busca transações onde a conta informada é a origem OU o destino, da mais recente para a mais antiga.
    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.numero = :numeroConta OR t.contaDestino.numero = :numeroConta ORDER BY t.dataHora DESC")
    List<Transacao> emitirExtrato(@Param("numeroConta") String numeroConta);
}