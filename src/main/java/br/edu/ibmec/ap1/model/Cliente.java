package br.edu.ibmec.ap1.model;
import br.edu.ibmec.ap1.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Cliente {

    private UUID id;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Por favor, digite seu nome.")
    private String nome;

    @NotBlank(message = "Por favor, digite seu e-mail.")
    @Email
    private String email;

    @CPF
    @Size(min = 14, max = 14, message = "Por favor, digite seu CPF com 11 dígitos no formato XXX.XXX.XXX-XX.")
    @NotBlank(message = "Por favor, digite seu CPF")
    private String cpf;

    @NotNull(message = "Por favor, digite sua data de nascimento")
    private LocalDate dataNascimento;

    @Size(min = 10, max = 11, message = "Por favor, digite seu telefone com o DDD e os dígitos de seu número de contato.")
    private String telefone;

    private List<Endereco> enderecos = new ArrayList<>();

    public void associarEndereco(Endereco endereco){
        this.enderecos.add(endereco);
    }

    public void excluirEndereco(Endereco endereco) throws Exception {
        if (!this.enderecos.remove(endereco)) {
            throw new Exception("Endereço não encontrado.");
        }
    }
}
