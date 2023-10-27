package com.certificate.microservice.infra;

import com.certificate.microservice.dtos.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public  ResponseEntity<ExceptionDTO> handleNotFound(NoSuchElementException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage() + " não encontrada.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
