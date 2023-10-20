package com.authentication.microservice.infra;

import com.authentication.microservice.exceptions.AuthenticationLoginException;
import com.authentication.microservice.dtos.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthenticationLoginException.CredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleCredentialsException(AuthenticationLoginException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage());
        return new ResponseEntity<ExceptionDTO>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationLoginException.RequiredField.class)
    public ResponseEntity<ExceptionDTO> handleActionCreationException(AuthenticationLoginException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage());
        return new ResponseEntity<ExceptionDTO>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleGenericException(Exception exception) {
        ExceptionDTO response = new ExceptionDTO("Erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

