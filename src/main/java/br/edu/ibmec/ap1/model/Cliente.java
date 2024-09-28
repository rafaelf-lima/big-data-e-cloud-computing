package br.edu.ibmec.ap1.model;
import br.edu.ibmec.ap1.model.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Size(min = 3, max = 100)
    @NotBlank(message = "Por favor, digite seu nome.")
    private String nome;

    @Column
    @NotBlank(message = "Por favor, digite seu e-mail.")
    @Email
    private String email;

    @Column
    @CPF
    @Size(min = 14, max = 14, message = "Por favor, digite seu CPF com 11 dígitos no formato XXX.XXX.XXX-XX.")
    @NotBlank(message = "Por favor, digite seu CPF")
    private String cpf;

    @Column
    @NotNull(message = "Por favor, digite sua data de nascimento")
    private LocalDate dataNascimento;

    @Column
    @Size(min = 10, max = 11, message = "Por favor, digite seu telefone com o DDD e os dígitos de seu número de contato.")
    @Pattern(regexp = "^\\d{11}$")
    private String telefone;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "cliente_id")
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
