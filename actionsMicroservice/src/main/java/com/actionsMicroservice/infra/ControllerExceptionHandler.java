package com.actionsMicroservice.infra;

import com.actionsMicroservice.dtos.ExceptionDTO;
import com.actionsMicroservice.exceptions.ActionCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ActionCreationException.class)
    public ResponseEntity<ExceptionDTO> handleActionCreationException(ActionCreationException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> treatDuplicatedEntry(DataIntegrityViolationException exception) {
        String errorMessage = exception.getMessage();
        String textResponse = "Erro de violação de integridade";

        if (errorMessage.contains("Unique index or primary key violation")) {
            int startIndex = errorMessage.indexOf("(") + 1;
            int endIndex = errorMessage.indexOf(" NULLS", startIndex);
            String field = errorMessage.substring(startIndex, endIndex);
            textResponse = "Erro de violação de integridade no campo: " + field;
        }

        ExceptionDTO response = new ExceptionDTO(textResponse);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public  ResponseEntity<ExceptionDTO> handleNotFound(NoSuchElementException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage() + " não encontrada.");
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleGenericException(Exception exception) {
        ExceptionDTO response = new ExceptionDTO("Erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
