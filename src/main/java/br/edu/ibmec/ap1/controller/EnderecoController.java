package br.edu.ibmec.ap1.controller;


import br.edu.ibmec.ap1.model.Cliente;
import br.edu.ibmec.ap1.model.Endereco;
import br.edu.ibmec.ap1.service.ClienteService;
import br.edu.ibmec.ap1.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cliente/{id}/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/associar-endereco")
    public ResponseEntity<Cliente> associarEndereco(@PathVariable("id") int id, @Valid @RequestBody Endereco endereco) throws Exception {
        Cliente response = enderecoService.associarEndereco(endereco, id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{enderecoId}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable("id") int id, @PathVariable int enderecoId) throws Exception {
        Endereco response = enderecoService.getEnderecoById(id, enderecoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Cliente> deleteEndereco(@PathVariable("id") int clienteId, @PathVariable("enderecoId") int enderecoId) throws Exception {
        Cliente response = enderecoService.deleteEndereco(clienteId, enderecoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<Cliente> atualizarEndereco(@PathVariable("id") int clienteId, @PathVariable("enderecoId") int enderecoId, @Valid @RequestBody Endereco novosDados) throws Exception {
        Cliente response = enderecoService.updateEndereco(clienteId, enderecoId, novosDados);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
