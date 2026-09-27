package com.avaliativo.estacionamento.repository;

import com.avaliativo.estacionamento.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
