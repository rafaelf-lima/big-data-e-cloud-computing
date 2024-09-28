package br.edu.ibmec.ap1.exception;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
}
