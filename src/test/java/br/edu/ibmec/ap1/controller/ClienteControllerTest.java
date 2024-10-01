package br.edu.ibmec.ap1.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import br.edu.ibmec.ap1.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.repository.ClienteRepository;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_create_cliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Rafael");
        cliente.setEmail("rafael@rafael.com");
        cliente.setCpf("128.802.797-47");
        cliente.setDataNascimento(LocalDate.of(2004, 1, 1));
        cliente.setTelefone("11111111111");

        Cliente savedCliente = new Cliente();
        savedCliente.setId(13);  // ID gerado automaticamente
        savedCliente.setNome(cliente.getNome());
        savedCliente.setEmail(cliente.getEmail());
        savedCliente.setCpf(cliente.getCpf());
        savedCliente.setDataNascimento(cliente.getDataNascimento());
        savedCliente.setTelefone(cliente.getTelefone());

        given(this.clienteService.createCliente(cliente)).willReturn(savedCliente);

        this.mvc
                .perform(MockMvcRequestBuilders
                        .post("/cliente")
                        .content(this.mapper.writeValueAsString(cliente))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(13)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", is("Rafael")));
    }

    @Test
    public void should_get_cliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Luana");
        cliente.setEmail("lua@lua.com");
        cliente.setDataNascimento(LocalDate.of(2004, 1, 1));
        cliente.setCpf("085.289.040-08");
        cliente.setTelefone("11111111111");
        cliente.setId(1);

        given(this.clienteService.getItem(1)).willReturn(cliente);

        this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", is("Luana")));
    }

    @Test
    public void should_get_cliente_with_not_found() throws Exception {

        given(this.clienteService.getItem(1)).willReturn(null);

        this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
