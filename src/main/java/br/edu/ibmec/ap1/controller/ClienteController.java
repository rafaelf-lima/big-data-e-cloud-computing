package br.edu.ibmec.ap1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import br.edu.ibmec.ap1.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> getCliente() {
        return new ResponseEntity<List<Cliente>>(service.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") UUID id) {
        Cliente response = service.getItem(id);
        if (response == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) throws Exception {
        Cliente response = service.createCliente(cliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") UUID id, @Valid @RequestBody Cliente novosDados) throws Exception{
        Cliente clienteAtualizado = service.updateCliente(id, novosDados);
        if (clienteAtualizado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") UUID id) {
        if (service.getItem(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.deleteCliente(id);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{id}/associar-endereco")
    public ResponseEntity<Cliente> associarEndereco(@PathVariable("id") UUID id, @Valid @RequestBody Endereco endereco) throws Exception {
        Cliente response = service.associarEndereco(endereco, id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/associar-endereco/{enderecoId}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable("id") UUID id, @PathVariable UUID enderecoId) throws Exception {
        Endereco response = service.getEnderecoById(id, enderecoId);
        if (response == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("{id}/excluir-endereco/{enderecoId}")
    public ResponseEntity<Cliente> deleteEndereco(@PathVariable("id") UUID clienteId, @PathVariable("enderecoId") UUID enderecoId) throws Exception {
        Cliente response = service.deleteEndereco(clienteId, enderecoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}/atualizar-endereco/{enderecoId}")
    public ResponseEntity<Cliente> atualizarEndereco(@PathVariable("id") UUID clienteId, @PathVariable("enderecoId") UUID enderecoId, @RequestBody Endereco novosDados) throws Exception {
        Cliente response = service.updateEndereco(clienteId, enderecoId, novosDados);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}