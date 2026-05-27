package com.example.api_financeira.dto;

import java.math.BigDecimal;

// O 'record' do Java cria automaticamente os getters sem precisarmos do Lombok!
public record TransacaoDTO(String numeroConta, BigDecimal valor) {
}