package br.edu.ibmec.ap1.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationMessageError {
    private String message = "Sua requisição possui erros, verifique, por favor.";
    private List<ValidationError> errors = new ArrayList<>();
}
