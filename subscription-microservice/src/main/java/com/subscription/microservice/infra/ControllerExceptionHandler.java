package com.subscription.microservice.infra;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.dtos.ExceptionDTO;
import com.subscription.microservice.exceptions.SubscriptionCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public class ControllerExceptionHandler {

    @ExceptionHandler(SubscriptionCreationException.class)
    public ResponseEntity<ExceptionDTO> handleSubscriptionCreationException(SubscriptionCreationException exception){
        ExceptionDTO response = new ExceptionDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> treatDuplicatedEntry(DataIntegrityViolationException exception){
        String errorMessage = exception.getMessage();
        String textResponse = "Erro de violação de integridade";

        if(errorMessage.contains("Unique index or primary key violation")){
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
