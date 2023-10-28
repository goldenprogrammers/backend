package com.subscription.microservice.controllers;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.dtos.*;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscription")
@PreAuthorize("hasAnyRole('admin','user')")
@Tag(name= "Inscrição", description = "Todos os endpoints de Inscrições")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{actionId}")
    @Transactional
    @Operation(summary = "Criação de inscrições")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrição realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class), examples = {
                            @ExampleObject(value = "{\"userId\": \"1\", \"actionId\": \"1\", \"formReceived\": \"true\", \"formResponseApproved\": \"true\", \"status\": \"IN_PROGRESS\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Erro na validação do campo", content = {
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
            @Parameter(description = "id do usuário que está se inscrevendo", example = "1", content = {
            @Content(mediaType = "number", schema = @Schema(implementation = Number.class))})
            @PathVariable long actionId, @RequestHeader("Authorization") String authorizationH){

        SubscriptionIdDTO newSubscriptionDTO = new SubscriptionIdDTO(authorizationH, actionId);
        Subscription newSubscription = subscriptionService.createSubscription(newSubscriptionDTO);
        return new ResponseEntity<>(newSubscription, HttpStatus.CREATED);
    }
    @GetMapping("/{actionId}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Buscar todos os usuários inscritos pela ação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso retornando pelo menos uma ação", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CompleteSubscriptionDTO.class), examples = {
                            @ExampleObject(value = "{\"id\": \"1\", \"actionId\": \"1\", \"nome\": \"Murilo Matias\", \"email\": \"murilo@gmail.com\",\"formReceived\": \"true\", \"formResponseApproved\": \"true\", \"status\": \"IN_PROGRESS\"}")
                    })
            }),
            @ApiResponse(responseCode = "204", description = "Busca realizada com sucesso, mas sem nenhum retorno", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<GetWithPaginationDTO> getSubscriptionByAction(
            @Parameter(description = "Quantidade de ações retornadas por página", example = "25", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "25") int pageSize,
            @Parameter(description = "Página a ser retornada", example = "1", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "1") int page,
            @PathVariable long actionId
    ) {
        Page<CompleteSubscriptionDTO> completeSubscription = subscriptionService.getSubscriptionByAction(page - 1, pageSize, actionId);
        if(completeSubscription.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        GetWithPaginationDTO response = new GetWithPaginationDTO(
                completeSubscription.getContent(),
                completeSubscription.getTotalPages(),
                completeSubscription.getTotalElements(),
                completeSubscription.getPageable().getPageNumber() + 1
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Atualizar o processo de uma inscrição")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"userId\": \"1\", \"actionId\": \"1\", \"formReceived\": \"false\", \"formResponseApproved\": \"false\", \"status\": \"IN_PROGRESS\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação atualizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class), examples = {
                            @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\" , \"isDeleted\": \"false\"}")
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
    public ResponseEntity<Subscription> updateSubscription(
            @Parameter(description = "id da inscrição que está sendo atualizada", example = "1", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))})
            @PathVariable long id,
            @RequestBody SubscriptionDTO data){
        SubscriptionIdDTO subs = new SubscriptionIdDTO(data.userId(), id);
        Subscription updatedSubscription = subscriptionService.updateSubscription(subs, data);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }
}
