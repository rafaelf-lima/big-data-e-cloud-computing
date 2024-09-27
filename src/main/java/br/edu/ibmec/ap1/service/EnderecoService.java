package br.edu.ibmec.ap1.service;

import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private ClienteService clienteService;

    public Cliente associarEndereco(Endereco endereco, UUID id) throws Exception {
        Cliente cliente = clienteService.findCliente(id);
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
        Cliente cliente = clienteService.findCliente(clienteId);
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
        Cliente cliente = clienteService.findCliente(id);
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
        Cliente cliente = clienteService.findCliente(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        for (Endereco endereco: cliente.getEnderecos()) {
            if (endereco.getId().equals(enderecoId)) {
                endereco.setRua(novoEnderecoASerAtualizado.getRua());
                endereco.setNumero(novoEnderecoASerAtualizado.getNumero());
                endereco.setBairro(novoEnderecoASerAtualizado.getBairro());
                endereco.setCidade(novoEnderecoASerAtualizado.getCidade());
                endereco.setEstado(novoEnderecoASerAtualizado.getEstado());
                endereco.setCep(novoEnderecoASerAtualizado.getCep());
                return cliente;
            }
        }
        throw new Exception("Endereço não encontrado.");
    }
}
