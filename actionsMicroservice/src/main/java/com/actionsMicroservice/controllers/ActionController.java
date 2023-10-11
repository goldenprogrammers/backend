package com.actionsMicroservice.controllers;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.dtos.ExceptionDTO;
import com.actionsMicroservice.services.ActionService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/action")
@Tag(name = "Ações", description = "Todos os endpoints de Ações")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping
    @Operation(summary = "Criação de ações")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": [12, 43], \"status\": \"active\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ação criada com sucesso", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Action.class), examples = {
                            @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Validação dos campos", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                        @ExampleObject(name = "title validation",value = "{\"message\": \"O campo title é obrigatório.\"}"),
                        @ExampleObject(name = "description validation", value = "{\"message\": \"O campo description é obrigatório.\"}"),
                        @ExampleObject(name = "form validation", value = "{\"message\": \"O campo formLink é obrigatório.\"}"),
                        @ExampleObject(name = "image validation", value = "{\"message\": \"O campo image é obrigatório.\"}"),
                        @ExampleObject(name = "status validation", value = "{\"message\": \"O campo status é obrigatório.\"}"),
                        @ExampleObject(name = "title length validation", value = "{\"message\": \"O título pode ter no máximo 80 caracteres.\"}"),
                        @ExampleObject(name = "description length validation", value = "{\"message\": \"A descrição pode ter no máximo 4096 caracteres.\"}")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<Action> createAction(@RequestBody ActionDTO action) {
        Action newAction = actionService.createAction(action);
        return new ResponseEntity<>(newAction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ação pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Action.class), examples = {
                            @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\"}")
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
    public ResponseEntity<Action> getActionById(@PathVariable long id) {
        Action newAction = actionService.getActionById(id);
        return new ResponseEntity<>(newAction, HttpStatus.OK);
    }
}
