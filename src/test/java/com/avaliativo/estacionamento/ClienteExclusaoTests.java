package com.avaliativo.estacionamento;

import com.avaliativo.estacionamento.controller.ClienteController;
import com.avaliativo.estacionamento.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteExclusaoTests {
    private ClienteRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        repository = mock(ClienteRepository.class);
        ClienteController controller = new ClienteController();
        ReflectionTestUtils.setField(controller, "repository", repository);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void exclusaoBloqueadaRetornaMensagemAmigavel() throws Exception {
        doThrow(new DataIntegrityViolationException("foreign key"))
                .when(repository).deleteById(7L);
        mvc.perform(delete("/clientes").param("id", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes/listagem"))
                .andExpect(flash().attribute("erroTitulo", "Este cliente possui veículos vinculados"))
                .andExpect(flash().attribute("erroMensagem",
                        "Para excluir o cliente, exclua ou transfira seus veículos para outro proprietário primeiro. Nenhum cadastro foi excluído."));
        verify(repository).deleteById(7L);
    }

    @Test
    void exclusaoPermitidaPreservaFluxoOriginal() throws Exception {
        mvc.perform(delete("/clientes").param("id", "8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes/listagem"))
                .andExpect(flash().attributeCount(0));
        verify(repository).deleteById(8L);
    }
}
