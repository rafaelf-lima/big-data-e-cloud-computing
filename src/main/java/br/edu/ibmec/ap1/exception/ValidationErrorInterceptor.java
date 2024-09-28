package br.edu.ibmec.ap1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationErrorInterceptor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationMessageError validationErrorHandler(MethodArgumentNotValidException e) {
        ValidationMessageError response = new ValidationMessageError();

        for (FieldError item : e.getFieldErrors()) {
            ValidationError error = new ValidationError();
            error.setField(item.getField());
            error.setMessage(item.getDefaultMessage());
            response.getErrors().add(error);
        }

        return response;
    }

    @ExceptionHandler(ClienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessageError validationHandlerCliente(ClienteException e) {
        ValidationMessageError response = new ValidationMessageError();
        ValidationError error = new ValidationError();
        error.setField("exception");
        error.setMessage(e.getMessage());
        response.getErrors().add(error);
        return response;
    }

    @ExceptionHandler(EnderecoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessageError validationHandlerEndereco(EnderecoException e) {
        ValidationMessageError response = new ValidationMessageError();
        ValidationError error = new ValidationError();
        error.setField("exception");
        error.setMessage(e.getMessage());
        response.getErrors().add(error);
        return response;
    }
}


