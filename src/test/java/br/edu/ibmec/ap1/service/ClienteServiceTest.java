package br.edu.ibmec.ap1.service;

import br.edu.ibmec.ap1.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class ClienteServiceTest {
    @Autowired
    private ClienteService service;

    @Test
    public void should_create_cliente() throws Exception{
        Cliente cliente = new Cliente();
        cliente.setNome("Rafael");
        cliente.setEmail("rafael@teste.com");
        cliente.setCpf("128.802.797-47");
        cliente.setTelefone("2222222222");
        cliente.setDataNascimento(LocalDate.of(2004, 1, 1));

        Cliente resultado = service.createCliente(cliente);
        UUID id = resultado.getId();

        Assertions.assertNotNull(resultado);
        Assertions.assertNotNull(resultado.getNome());
        Assertions.assertNotNull(resultado.getCpf());
        Assertions.assertNotNull(resultado.getEmail());
        Assertions.assertNotNull(resultado.getTelefone());
        Assertions.assertNotNull(resultado.getDataNascimento());
        Assertions.assertEquals(id, cliente.getId());
    }


    @Test
    public void should_not_accept_duplicate_cpf() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Rafael");
        cliente1.setCpf("128.802.797-47");
        cliente1.setEmail("rafael@email.com");
        cliente1.setTelefone("1234256789");
        cliente1.setDataNascimento(LocalDate.of(2002, 1, 1));
        service.createCliente(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cláudio");
        cliente2.setCpf("128.802.797-47");
        cliente2.setEmail("Cláudio@email.com");
        cliente2.setTelefone("2222222222");
        cliente2.setDataNascimento(LocalDate.of(2000, 10, 19));

        Assertions.assertThrows(Exception.class, () -> {
            service.createCliente(cliente2);
        });
    }

    @Test
    public void should_not_accept_invalid_cpf() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@teste.com.br");
        cliente.setCpf("123.456.111-11");
        cliente.setDataNascimento(LocalDate.of(2000, 12, 12));
        cliente.setTelefone("1111111111");

        Assertions.assertThrows(Exception.class, () -> {
            service.createCliente(cliente);
        });
    }
}
