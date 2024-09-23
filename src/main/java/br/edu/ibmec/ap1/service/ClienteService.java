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

    public Cliente updateCliente(UUID id, Cliente novoCliente) throws Exception{
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
            if (c.getEmail().equals(novoCliente.getEmail())) {
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

    public Cliente associarEndereco(Endereco endereco, UUID id) throws Exception {
        Cliente cliente = this.findCliente(id);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        endereco.setId(UUID.randomUUID());
        cliente.associarEndereco(endereco);
        return cliente;
    }

    public Endereco getEnderecoById(UUID clienteId,UUID  enderecoId) throws Exception{
            return findEndereco(clienteId, enderecoId);
    }

    public Endereco findEndereco(UUID clienteId, UUID enderecoId) throws Exception {
        Cliente cliente = this.findCliente(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        for (Endereco endereco : cliente.getEnderecos()) {
            if (endereco.getId().equals(enderecoId)) {
                return endereco;
            }
        }
        throw new Exception("O endereço não foi encontrado.");
    }

    public Cliente deleteEndereco(UUID id, UUID enderecoId) throws Exception {
        Cliente cliente = this.findCliente(id);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        Endereco response = null;
        for (Endereco endereco : cliente.getEnderecos()) {
            if (endereco.getId().equals(enderecoId)) {
                response = endereco;
                break;
            }
        }
        if (response != null) {
            cliente.excluirEndereco(response);
        } else {
            throw new Exception("Endereço não encontrado.");
        }
        return cliente;
    }

    public Cliente updateEndereco(UUID clienteId, UUID enderecoId, Endereco novoEnderecoASerAtualizado) throws Exception {
        Cliente cliente = this.findCliente(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        for (Endereco endereco: cliente.getEnderecos()) {
            if (endereco.getId().equals(enderecoId)) {
                endereco.setRua(novoEnderecoASerAtualizado.getRua());
                endereco.setCidade(novoEnderecoASerAtualizado.getCidade());
                endereco.setEstado(novoEnderecoASerAtualizado.getEstado());
                endereco.setCep(novoEnderecoASerAtualizado.getCep());
                return cliente;
            }
        }
        throw new Exception("Endereço não encontrado.");
    }
}
