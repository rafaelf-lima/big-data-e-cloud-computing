package br.edu.ibmec.ap1.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class Endereco {

    private UUID id;

    @Size(min = 3, max = 255)
    @NotBlank(message = "Por favor, digite a rua.")
    private String rua;

    @NotBlank(message = "Por favor, digite o número.")
    private String numero;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Por favor, digite o bairro.")
    private String bairro;

    @Size(min = 2, max = 100)
    @NotBlank(message = "Por favor, digite a cidade.")
    private String cidade;

    @NotNull(message = "Por favor, digite a sigla do estado.")
    private Estado estado;

    @NotBlank(message = "Por favor, digite o CEP no formato XXXXX-XXX.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$")
    private String cep;

}
