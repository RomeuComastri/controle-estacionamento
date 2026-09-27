package com.avaliativo.estacionamento.controller;

import com.avaliativo.estacionamento.dto.DadosAlteracaoVeiculo;
import com.avaliativo.estacionamento.dto.DadosCadastroVeiculo;
import com.avaliativo.estacionamento.model.Veiculo;
import com.avaliativo.estacionamento.repository.ClienteRepository;
import com.avaliativo.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private ClienteRepository repositoryCliente;

    // ---- CREATE / formulário ----
    @GetMapping("/formulario")
    public String carregaFormulario(Long id, Model model) {
        if (id != null) {
            Veiculo veiculo = repository.getReferenceById(id);
            model.addAttribute("veiculo", veiculo);
        }
        model.addAttribute("listaCliente", repositoryCliente.findAll());
        return "veiculos/formulario";
    }

    @PostMapping("/formulario")
    public String cadastraVeiculo(DadosCadastroVeiculo dados) {
        Veiculo veiculo = new Veiculo(dados);
        repository.save(veiculo);
        return "redirect:/veiculos/listagem";
    }

    // ---- READ ----
    @GetMapping("/listagem")
    public String carregaListagem(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "veiculos/listagem";
    }

    // ---- UPDATE ----
    @PutMapping("/formulario")
    @Transactional
    public String alteraVeiculo(DadosAlteracaoVeiculo dados) {
        Veiculo veiculo = repository.getReferenceById(dados.id());
        veiculo.atualizaDados(dados);
        return "redirect:/veiculos/listagem";
    }

    // ---- DELETE ----
    @DeleteMapping
    @Transactional
    public String removeVeiculo(Long id) {
        repository.deleteById(id);
        return "redirect:/veiculos/listagem";
    }

    // ---- Requisito 4: consulta usando Derived Query ----
    @GetMapping("/buscar")
    public String carregaBusca(String cor, Model model) {
        if (cor != null && !cor.isBlank()) {
            model.addAttribute("lista", repository.findByCorIgnoreCase(cor));
            model.addAttribute("cor", cor);
        }
        return "veiculos/buscar";
    }
}
