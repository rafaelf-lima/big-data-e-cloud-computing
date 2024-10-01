package br.edu.ibmec.ap1.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Size(min = 3, max = 255, message="Insira um nome correto")
    @NotBlank(message = "Por favor, digite a rua.")
    private String rua;

    @Column
    @NotBlank(message = "Por favor, digite o número.")
    private String numero;

    @Column
    @Size(min = 3, max = 100, message="Insira um nome correto")
    @NotBlank(message = "Por favor, digite o bairro.")
    private String bairro;

    @Column
    @Size(min = 2, max = 100, message="Insira um nome correto")
    @NotBlank(message = "Por favor, digite a cidade.")
    private String cidade;

    @Column
    @NotNull(message = "Por favor, digite a sigla do estado.")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column
    @NotBlank(message = "Por favor, digite o CEP no formato XXXXX-XXX.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$")
    private String cep;
}
