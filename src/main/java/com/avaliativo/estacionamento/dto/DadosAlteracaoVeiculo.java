package com.avaliativo.estacionamento.dto;

import com.avaliativo.estacionamento.model.Cliente;

public record DadosAlteracaoVeiculo(Long id, String placa, String modelo, String cor, Cliente cliente) {
}
