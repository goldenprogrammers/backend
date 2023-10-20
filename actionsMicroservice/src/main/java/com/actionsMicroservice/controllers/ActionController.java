package com.actionsMicroservice.controllers;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.domain.action.ActionStatus;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.dtos.ActionStatusDTO;
import com.actionsMicroservice.dtos.ExceptionDTO;
import com.actionsMicroservice.dtos.GetWithPaginationDTO;
import com.actionsMicroservice.services.ActionService;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/action")
@CrossOrigin(origins = "*")
@Tag(name = "Ações", description = "Todos os endpoints de Ações")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping
    @Operation(summary = "Criação de ações")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ação criada com sucesso", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Action.class), examples = {
                                @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\", \"isDeleted\": \"false\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Validação dos campos", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                        @ExampleObject(name = "title validation",value = "{\"message\": \"O campo title é obrigatório.\"}"),
                        @ExampleObject(name = "description validation", value = "{\"message\": \"O campo description é obrigatório.\"}"),
                        @ExampleObject(name = "form validation", value = "{\"message\": \"O campo formLink é obrigatório.\"}"),
                        @ExampleObject(name = "image validation", value = "{\"message\": \"O campo image é obrigatório.\"}"),
                        @ExampleObject(name = "image size validation", value = "{\"message\": \"O tamanho da imagem deve ser de no máximo 2MB.\"}"),
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
                            @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\", \"isDeleted\": \"false\"}")
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
    public ResponseEntity<Action> getActionById(@Parameter(description = "id da ação que está sendo buscada", example = "1", content = { @Content(mediaType = "number", schema = @Schema(implementation = Number.class)) })
            @PathVariable long id) {
        Action newAction = actionService.getActionById(id);
        return new ResponseEntity<>(newAction, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Buscar todas as ações disponíveis com filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso retornando pelo menos uma ação", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Action.class), examples = {
                            @ExampleObject(value = "{\"data\": [{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\", \"isDeleted\": \"false\"}], \"totalPages\": 1, \"totalElements\": 1, \"pageNumber\": 1 }")
                    })
            }),
            @ApiResponse(responseCode = "204", description = "Busca realizada com sucesso, mas sem nenhum retorno", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<GetWithPaginationDTO> getAllActions(
            @Parameter(description = "Quantidade de ações retornadas por página", example = "25", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "25") int pageSize,
            @Parameter(description = "Página a ser retornada", example = "1", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "1") int page,
            @Parameter(description = "Status da ação")
            @RequestParam(required = false) ActionStatus status,
            @Parameter(description = "Define se a ordenação pela data de criação será de maneira ascendente ou descendente")
            @RequestParam(required = false) Sort.Direction sort
    ) {
        Page<Action> actions = actionService.getActions(page - 1, pageSize, sort, status);

        if (actions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        GetWithPaginationDTO response = new GetWithPaginationDTO(
                actions.getContent(),
                actions.getTotalPages(),
                actions.getTotalElements(),
                actions.getPageable().getPageNumber() + 1
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar todas as ações ativas que contem o termo da busca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso retornando pelo menos uma ação", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetWithPaginationDTO.class), examples = {
                            @ExampleObject(value = "{\"data\": [{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\", \"isDeleted\": \"false\"}], \"totalPages\": 1, \"totalElements\": 1, \"pageNumber\": 1 }")
                    })
            }),
            @ApiResponse(responseCode = "204", description = "Busca realizada com sucesso, mas sem nenhum retorno", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<GetWithPaginationDTO> getActionsByParcialTitle(
            @Parameter(description = "Quantidade de ações retornadar por página", example = "1", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "25") int pageSize,
            @Parameter(description = "Página a ser retornada", example = "25", content = {
                    @Content(mediaType = "number", schema = @Schema(implementation = Number.class))
            })
            @RequestParam(required = false, defaultValue = "1") int page,
            @Parameter(description = "Define se a ordenação pela data de criação será de maneira ascendente ou descendente")
            @RequestParam(required = false) Sort.Direction sort,
            @Parameter(description = "Termo da busca (não faz distinção entre letras maiúsculas e minúsculas)", example = "T")
            @RequestParam(required = false) String title
    ) {
        Page<Action> actions = actionService.getActionByTitle(page - 1, pageSize, sort, title);

        if (actions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        GetWithPaginationDTO response = new GetWithPaginationDTO(
                actions.getContent(),
                actions.getTotalPages(),
                actions.getTotalElements(),
                actions.getPageable().getPageNumber() + 1
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar dados de uma ação")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"title\": \"título\", \"description\": \"descrição\", \"image\": \"DCs=\", \"status\": \"active\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação atualizada com sucesso", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Action.class), examples = {
                            @ExampleObject(value = "{\"id\": 1, \"title\": \"título\", \"description\": \"descrição\", \"formLink\": \"www.formLink.com\", \"image\": \"DCs=\", \"status\": \"active\" , \"isDeleted\": \"false\"}")
                    })
            }),
            @ApiResponse(responseCode = "404", description = "Ação não encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Ação não encontrada.\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "O campo não pode ser atualizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"A atualização do campo 'formLink' não é permitida.\"}")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<Action> updateAction(@Parameter(description = "id da ação que está sendo alterada", example = "1", content = { @Content(mediaType = "number", schema = @Schema(implementation = Number.class)) })
            @PathVariable long id,
            @RequestBody ActionDTO data) {
        Action updatedAction = actionService.updateAction(id, data);
        return new ResponseEntity<>(updatedAction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deletar ações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ação excluída com sucesso", content = @Content),
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
    public ResponseEntity<?> deleteAction(@Parameter(description = "Id da ação que será excluída", example = "1", content = { @Content(mediaType = "number", schema = @Schema(implementation = Number.class)) })
            @PathVariable long id) {
        actionService.removeAction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("isactive/{id}")
    @Operation(summary = "Verificar se a ação está ativa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ActionStatusDTO.class), examples = {
                            @ExampleObject(value = "{\"isActive\": \"true||false\"}")
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
    public ResponseEntity<ActionStatusDTO> isAnActiveAction(@Parameter(description = "id da ação que está sendo verificada", example = "1", content = { @Content(mediaType = "number", schema = @Schema(implementation = Number.class)) })
            @PathVariable long id) {
        ActionStatusDTO isActive = new ActionStatusDTO(actionService.activeAction(id));
        return new ResponseEntity<>(isActive, HttpStatus.OK);
    }
}