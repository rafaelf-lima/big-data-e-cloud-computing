package br.edu.ibmec.ap1.service;


import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    private static List<Cliente> Clientes = new ArrayList<>();

    public List<Cliente> getAllItems(){
        return ClienteService.Clientes;
    }

    public Cliente getItem(UUID id) {
        return findCliente(id);
    }

    public Cliente findCliente(UUID id){
        Cliente response = null;

        for (Cliente cliente : Clientes){
            if (cliente.getId().equals(id)){
                response = cliente;
                break;
            }
        }
        return response;
    }

    public Cliente createCliente(Cliente item) throws Exception {
        if(!verificaIdade(item.getDataNascimento())) {
            throw new Exception("Idade do cliente deve ser maior que 18 anos.");
        }
        for (Cliente c : Clientes) {
            if (c.getCpf().equals(item.getCpf())) {
                throw new Exception("CPF já cadastrado.");
            }
        }
        for (Cliente c : Clientes) {
            if (c.getEmail().equals(item.getEmail())) {
                throw new Exception("E-mail já cadastrado.");
            }
        }
        item.setId(UUID.randomUUID());
        ClienteService.Clientes.add(item);
        return item;
    }

    public Cliente updateCliente(UUID id, Cliente novoCliente) throws Exception {
        Cliente novoClienteAtualizado = findCliente(id);
        if (novoClienteAtualizado == null)
            return null;

        if (!novoClienteAtualizado.getCpf().equals(novoCliente.getCpf())) {
            throw new Exception("Não é permitido alterar o CPF.");
        }
        if (!novoClienteAtualizado.getDataNascimento().equals(novoCliente.getDataNascimento())) {
            throw new Exception("Não é permitido alterar a data de nascimento.");
        }
        for (Cliente c : Clientes) {
            if (!c.getId().equals(id) && c.getEmail().equals(novoCliente.getEmail())) {
                throw new Exception("E-mail já cadastrado.");
            }
        }

        novoClienteAtualizado.setNome(novoCliente.getNome());
        novoClienteAtualizado.setEmail(novoCliente.getEmail());
        novoClienteAtualizado.setTelefone(novoCliente.getTelefone());
        novoClienteAtualizado.setEnderecos(novoCliente.getEnderecos());

        return novoClienteAtualizado;
    }

    public void deleteCliente(UUID id){
        Cliente clienteExcluido = findCliente(id);

        if (clienteExcluido == null)
            return;

        Clientes.remove(clienteExcluido);
    }
    public boolean verificaIdade(LocalDate dataNascimento){
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        return idade >= 18;
    }
}
