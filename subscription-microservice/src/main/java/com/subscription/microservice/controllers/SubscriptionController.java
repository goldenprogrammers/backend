package com.subscription.microservice.controllers;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.dtos.ExceptionDTO;
import com.subscription.microservice.dtos.SubscriptionDTO;
import com.subscription.microservice.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/subscription")
@Tag(name= "Inscrição", description = "Todos os endpoints de Inscrições")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "Criação de inscrições")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"userId\": \"1\", \"actionId\": \"1\", \"formReceived\": \"true\", \"formResponseApproved\": \"true\", \"status\": \"IN_PROGRESS\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrisção realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class), examples = {
                            @ExampleObject(value = "{\"userId\": \"1\", \"actionId\": \"1\", \"formReceived\": \"true\", \"formResponseApproved\": \"true\", \"status\": \"IN_PROGRESS\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Validação dos campos", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                    @ExampleObject(name = "userId validation",value = "{\"message\": \"O campo userId é obrigatório.\"}"),
                    @ExampleObject(name = "actionId validation", value = "{\"message\": \"O campo actionId é obrigatório.\"}"),
            })
    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })

    })
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionDTO subscription){
        Subscription newSubscription = subscriptionService.createSubscription(subscription);
        return new ResponseEntity<>(newSubscription, HttpStatus.CREATED);
    }
}
