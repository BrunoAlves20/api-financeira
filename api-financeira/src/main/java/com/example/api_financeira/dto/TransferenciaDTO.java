package com.example.api_financeira.dto;

import java.math.BigDecimal;

public record TransferenciaDTO(String numeroContaOrigem, String numeroContaDestino, BigDecimal valor) {
}