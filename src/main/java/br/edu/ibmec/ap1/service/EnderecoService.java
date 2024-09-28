package br.edu.ibmec.ap1.service;

import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import br.edu.ibmec.ap1.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente associarEndereco(Endereco endereco, int id) throws Exception {
        Cliente cliente = clienteService.getItem(id);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        cliente.associarEndereco(enderecoSalvo);

        clienteService.updateCliente(cliente.getId(), cliente);
        return cliente;
    }

    public Endereco getEnderecoById(int clienteId, int enderecoId) throws Exception {
        return findEndereco(clienteId, enderecoId);
    }

    public Endereco findEndereco(int clienteId, int enderecoId) throws Exception {
        Cliente cliente = clienteService.getItem(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }

        for (Endereco endereco : cliente.getEnderecos()) {
            if (endereco.getId() == enderecoId) {
                return endereco;
            }
        }
        throw new Exception("O endereço não foi encontrado ou não pertence a este cliente.");
    }

    public Cliente deleteEndereco(int clienteId, int enderecoId) throws Exception {
        Cliente cliente = clienteService.getItem(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        Endereco enderecoExcluido = null;
        for (Endereco endereco : cliente.getEnderecos()) {
            if (endereco.getId() == enderecoId) {
                enderecoExcluido = endereco;
                break;
            }
        }
        if (enderecoExcluido != null) {
            cliente.excluirEndereco(enderecoExcluido);
            enderecoRepository.delete(enderecoExcluido);
            return cliente;
        } else {
            throw new Exception("Endereço não encontrado.");
        }
    }

    public Cliente updateEndereco(int clienteId, int enderecoId, Endereco novoEnderecoASerAtualizado) throws Exception {
        Cliente cliente = clienteService.getItem(clienteId);
        if (cliente == null) {
            throw new Exception("O cliente não foi encontrado.");
        }
        for (Endereco endereco : cliente.getEnderecos()) {
            if (endereco.getId() == enderecoId) {
                endereco.setRua(novoEnderecoASerAtualizado.getRua());
                endereco.setNumero(novoEnderecoASerAtualizado.getNumero());
                endereco.setBairro(novoEnderecoASerAtualizado.getBairro());
                endereco.setCidade(novoEnderecoASerAtualizado.getCidade());
                endereco.setEstado(novoEnderecoASerAtualizado.getEstado());
                endereco.setCep(novoEnderecoASerAtualizado.getCep());
                enderecoRepository.save(endereco);
                return cliente;
            }
        }
        throw new Exception("Endereço não encontrado.");
    }
}