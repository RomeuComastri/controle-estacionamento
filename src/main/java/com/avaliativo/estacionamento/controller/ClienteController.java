package com.avaliativo.estacionamento.controller;

import com.avaliativo.estacionamento.dto.DadosAlteracaoCliente;
import com.avaliativo.estacionamento.dto.DadosCadastroCliente;
import com.avaliativo.estacionamento.model.Cliente;
import com.avaliativo.estacionamento.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // ---- CREATE / formulário ----
    @GetMapping("/formulario")
    public String carregaFormulario(Long id, Model model) {
        if (id != null) {
            Cliente cliente = repository.getReferenceById(id);
            model.addAttribute("cliente", cliente);
        }
        return "clientes/formulario";
    }

    @PostMapping("/formulario")
    public String cadastraCliente(DadosCadastroCliente dados) {
        Cliente cliente = new Cliente(dados);
        repository.save(cliente);
        return "redirect:/clientes/listagem";
    }

    // ---- READ ----
    @GetMapping("/listagem")
    public String carregaListagem(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "clientes/listagem";
    }

    // ---- UPDATE ----
    @PutMapping("/formulario")
    @Transactional
    public String alteraCliente(DadosAlteracaoCliente dados) {
        Cliente cliente = repository.getReferenceById(dados.idCliente());
        cliente.atualizaDados(dados);
        return "redirect:/clientes/listagem";
    }

    // ---- DELETE ----
    @DeleteMapping
    @Transactional
    public String removeCliente(Long id) {
        repository.deleteById(id);
        return "redirect:/clientes/listagem";
    }

    // Executado após o encerramento da transação, inclusive se a falha ocorrer no commit.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String trataIntegridade(DataIntegrityViolationException exception,
                                  HttpServletRequest request, RedirectAttributes attributes) {
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            attributes.addFlashAttribute("erroTitulo", "Este cliente possui veículos vinculados");
            attributes.addFlashAttribute("erroMensagem",
                    "Para excluir o cliente, exclua ou transfira seus veículos para outro proprietário primeiro. Nenhum cadastro foi excluído.");
        } else {
            attributes.addFlashAttribute("erroTitulo", "Não foi possível salvar o cliente");
            attributes.addFlashAttribute("erroMensagem",
                    "Os dados entram em conflito com um cadastro existente. Confira as informações e tente novamente.");
        }
        return "redirect:/clientes/listagem";
    }
}
