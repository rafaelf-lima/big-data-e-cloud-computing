package br.edu.ibmec.ap1.service;


import br.edu.ibmec.ap1.exception.ClienteException;
import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import br.edu.ibmec.ap1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllItems() {
        return clienteRepository.findAll();
    }

    public Cliente getItem(int id) throws Exception {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado."));
    }

    public Cliente createCliente(Cliente item) throws Exception {
        if (!verificaIdade(item.getDataNascimento())) {
            throw new ClienteException("Idade do cliente deve ser maior que 18 anos.");
        }

        Optional<Cliente> clienteExistentePorCpf = clienteRepository.findByCpf(item.getCpf());
        if (clienteExistentePorCpf.isPresent()) {
            throw new ClienteException("CPF já cadastrado.");
        }

        Optional<Cliente> clienteExistentePorEmail = clienteRepository.findByEmail(item.getEmail());
        if (clienteExistentePorEmail.isPresent()) {
            throw new ClienteException("E-mail já cadastrado.");
        }

        return clienteRepository.save(item);
    }

    public Cliente updateCliente(int id, Cliente novoCliente) throws Exception {
        Cliente clienteAtualizado = getItem(id);

        if (!clienteAtualizado.getCpf().equals(novoCliente.getCpf())) {
            throw new ClienteException("Não é permitido alterar o CPF.");
        }
        if (!clienteAtualizado.getDataNascimento().equals(novoCliente.getDataNascimento())) {
            throw new ClienteException("Não é permitido alterar a data de nascimento.");
        }

        Optional<Cliente> clienteExistentePorEmail = clienteRepository.findByEmail(novoCliente.getEmail());
        if (clienteExistentePorEmail.isPresent() &&
                clienteExistentePorEmail.get().getId() != (clienteAtualizado.getId())) {
            throw new ClienteException("E-mail já cadastrado.");
        }

        clienteAtualizado.setNome(novoCliente.getNome());
        clienteAtualizado.setEmail(novoCliente.getEmail());
        clienteAtualizado.setTelefone(novoCliente.getTelefone());
        clienteAtualizado.setEnderecos(novoCliente.getEnderecos());

        return clienteRepository.save(clienteAtualizado);
    }

    public void deleteCliente(int id) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    public boolean verificaIdade(LocalDate dataNascimento) {
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        return idade >= 18;
    }
}