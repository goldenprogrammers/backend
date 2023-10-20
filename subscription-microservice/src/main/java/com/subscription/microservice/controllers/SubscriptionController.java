package com.subscription.microservice.controllers;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.dtos.ExceptionDTO;
import com.subscription.microservice.dtos.SubscriptionIdDTO;
import com.subscription.microservice.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "*")
@Tag(name= "Inscrição", description = "Todos os endpoints de Inscrições")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{actionId}")
    @Operation(summary = "Criação de inscrições")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrisção realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class), examples = {
                            @ExampleObject(value = "{\"userId\": \"1\", \"actionId\": \"1\", \"formReceived\": \"true\", \"formResponseApproved\": \"true\", \"status\": \"IN_PROGRESS\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Erroa na validação do campo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(name = "userId validation",value = "{\"message\": \"O campo userId é obrigatório.\"}"),
                            @ExampleObject(name = "actionId validation", value = "{\"message\": \"O campo actionId é obrigatório.\"}")
                    })
            }),
            @ApiResponse(responseCode = "404", description = "Ação não encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Ação não encontrada.\"}")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })

    })
    public ResponseEntity<Subscription> createSubscription(
            @Parameter(description = "id da usuário que está se inscrevendo", example = "1", content = {
            @Content(mediaType = "number", schema = @Schema(implementation = Number.class))})
            @PathVariable long actionId, @RequestHeader("Authorization") String authorizationH){

        SubscriptionIdDTO newSubscriptionDTO = new SubscriptionIdDTO(authorizationH, actionId);
        Subscription newSubscription = subscriptionService.createSubscription(newSubscriptionDTO);
        return new ResponseEntity<>(newSubscription, HttpStatus.CREATED);
    }
}
