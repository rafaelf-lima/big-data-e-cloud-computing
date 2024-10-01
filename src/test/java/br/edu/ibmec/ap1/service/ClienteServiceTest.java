package br.edu.ibmec.ap1.service;

import br.edu.ibmec.ap1.exception.ClienteException;
import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import br.edu.ibmec.ap1.model.Estado;
import br.edu.ibmec.ap1.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente clientePadrao;

    @Test
    public void should_create_cliente() throws Exception{
        clientePadrao = new Cliente();
        clientePadrao.setNome("Rafael");
        clientePadrao.setEmail("rafaasdasdadadsel@teste.com");
        clientePadrao.setCpf("545.853.110-84");
        clientePadrao.setTelefone("22222222222");
        clientePadrao.setDataNascimento(LocalDate.of(2004, 1, 1));

        Cliente clienteCriado = clienteRepository.save(clientePadrao);

        assertNotNull(clienteCriado.getId());
        assertEquals("Rafael", clienteCriado.getNome());
        assertEquals("rafaasdasdadadsel@teste.com", clienteCriado.getEmail());
        assertEquals("545.853.110-84", clienteCriado.getCpf());
        assertEquals("22222222222", clienteCriado.getTelefone());
        assertEquals(LocalDate.of(2004, 1, 1), clienteCriado.getDataNascimento());

    }



    @Test
    public void should_not_accept_invalid_cpf() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@teste.com.br");
        cliente.setCpf("123.456.111-11");
        cliente.setDataNascimento(LocalDate.of(2000, 12, 12));
        cliente.setTelefone("22222222222");

        Assertions.assertThrows(Exception.class, () -> {
            service.createCliente(cliente);
        });
    }

    @Test
    public void should_not_accept_duplicate_cpf() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Rafael");
        cliente1.setCpf("688.117.290-76");
        cliente1.setEmail("raf@email.com");
        cliente1.setTelefone("21212121212");
        cliente1.setDataNascimento(LocalDate.of(1990, 1, 1));
        service.createCliente(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Rafael");
        cliente2.setCpf("688.117.290-76");
        cliente2.setEmail("rafa@email.com");
        cliente2.setTelefone("21212121212");
        cliente2.setDataNascimento(LocalDate.of(1990, 12, 11));

        Assertions.assertThrows(Exception.class, () -> {
            service.createCliente(cliente2);
        });
    }

}
