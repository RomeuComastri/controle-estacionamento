package com.avaliativo.estacionamento.model;

import com.avaliativo.estacionamento.dto.DadosAlteracaoVeiculo;
import com.avaliativo.estacionamento.dto.DadosCadastroVeiculo;
import jakarta.persistence.*;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String modelo;
    private String cor;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    public Veiculo() {
    }

    public Veiculo(DadosCadastroVeiculo dados) {
        this.placa = dados.placa();
        this.modelo = dados.modelo();
        this.cor = dados.cor();
        this.cliente = dados.cliente();
    }

    public void atualizaDados(DadosAlteracaoVeiculo dados) {
        this.placa = dados.placa();
        this.modelo = dados.modelo();
        this.cor = dados.cor();
        this.cliente = dados.cliente();
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
