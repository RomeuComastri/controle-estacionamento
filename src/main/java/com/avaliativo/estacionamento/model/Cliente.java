package com.avaliativo.estacionamento.model;

import com.avaliativo.estacionamento.dto.DadosAlteracaoCliente;
import com.avaliativo.estacionamento.dto.DadosCadastroCliente;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nome;
    private String cpf;
    private String telefone;

    @OneToMany(mappedBy = "cliente")
    private List<Veiculo> listaVeiculo = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(DadosCadastroCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
    }

    public void atualizaDados(DadosAlteracaoCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }
}
