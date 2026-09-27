package com.avaliativo.estacionamento.repository;

import com.avaliativo.estacionamento.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // Derived Query: gera a consulta automaticamente a partir do nome do método,
    // buscando todos os veículos de determinada cor (ignorando maiúsculas/minúsculas)
    List<Veiculo> findByCorIgnoreCase(String cor);
}
