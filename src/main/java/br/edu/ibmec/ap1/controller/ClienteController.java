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
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") int id) throws Exception{
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
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") int id, @Valid @RequestBody Cliente novosDados) throws Exception{
        Cliente clienteAtualizado = service.updateCliente(id, novosDados);
        if (clienteAtualizado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") int id) throws Exception{
        if (service.getItem(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.deleteCliente(id);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}