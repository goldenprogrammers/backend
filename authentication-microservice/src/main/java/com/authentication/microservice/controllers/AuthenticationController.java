package com.authentication.microservice.controllers;

import com.authentication.microservice.domain.authentication.Authentication;
import com.authentication.microservice.dtos.AuthenticationDTO;
import com.authentication.microservice.dtos.ExceptionDTO;
import com.authentication.microservice.services.AuthenticationService;
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

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "Todos os endpoints relativos a autenticação de Usuários")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário ou administrador")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = "{\"credential\": \"test@test.com\", \"password\": \"senha123\"}")
    }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário autenticado", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Authentication.class), examples = {
                            @ExampleObject(value = "{" +
                                    "\"accessToken\": \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOcFRzWG42RUtIdWFMaVdrWWFtNjg3VkswU1c3aHRDNEZBVmhDdXpzLUZvIn0.eyJleHAiOjE2OTc3MTIzMTYsImlhdCI6MTY5NzcwNTExNiwianRpIjoiOWMyYjIzY2UtMjRmMS00N2JmLTg4YTQtY2U4ODkyMGVhNTg4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMzg5NWFiNzctOTc3Ni00YTJiLWE0ZDAtMTE1MGYxMTE0ZWMxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicGFudGFuYWwtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjE5ZWI3YzUzLWRhMzctNGFhZS1iZjkyLWE2NjJmZjA5YjFiYSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wYW50YW5hbC1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjE5ZWI3YzUzLWRhMzctNGFhZS1iZjkyLWE2NjJmZjA5YjFiYSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InRlc3QgdGVzdCIsInByZWZlcnJlZF91c2VybmFtZSI6InRlc3QiLCJnaXZlbl9uYW1lIjoidGVzdCIsImZhbWlseV9uYW1lIjoidGVzdCIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSJ9.XDaoy5UyASSxVbeAoqPLTv6VKdYFEL46C6j4yZ300DJOzswC_8BGQVsfG7CwjOnq8endv9w19hJJ45pjVTS-vCdMMyHZ7-QUyknU9lwzdQXI8T-cIo771q8szCkVhsXWeLgMeDndQqMTkydU6yEOAhlSvquWkkxVrsZTMNpPmfdtOS4UzcCd4ouqjFvglTJLUIIxtvtKc0BBWwkz9e_yfDhIPRGhI5YfdOwQXITh0WonrHUxiKmO_6u0npPCknmlxReu4DJoMz1S7LcU0BrKu2RJ0qlOhLTlUF-vazvnPcv3Aiwjn7YxhEa5Wa9MzCY71rgqdfIM1zdHKM8ls3OY1Q\", " +
                                    "\"expiresIn\": \"1697745481\", " +
                                    "\"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjMTMwNWU0Ny1jMDlkLTRlNjMtOGFlMi02OTRkYTM5ZjI5OTkifQ.eyJleHAiOjE2OTc3MDY5MTYsImlhdCI6MTY5NzcwNTExNiwianRpIjoiMmEyN2JhZGQtMjE3MS00YTVhLTg5NWEtZmIwN2VjNzVjZTIyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvcmVhbG1zL3BhbnRhbmFsLWRldiIsInN1YiI6IjM4OTVhYjc3LTk3NzYtNGEyYi1hNGQwLTExNTBmMTExNGVjMSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwYW50YW5hbC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiMTllYjdjNTMtZGEzNy00YWFlLWJmOTItYTY2MmZmMDliMWJhIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiMTllYjdjNTMtZGEzNy00YWFlLWJmOTItYTY2MmZmMDliMWJhIn0.z2iOw9qq0X7bUq8f6xUrdMQ7bxqvwMvMYps18sZRkiY\"}")
                    })
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Usuário ou senha incorretos.\"}")
                    })
            }),
            @ApiResponse(responseCode = "400", description = "Validação dos campos", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(name = "credential validation", value = "{\"message\": \"O campo credential é obrigatório.\"}"),
                            @ExampleObject(name = "password validation", value = "{\"message\": \"O campo password é obrigatório.\"}")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class), examples = {
                            @ExampleObject(value = "{\"message\": \"Erro interno no servidor.\"}")
                    })
            })
    })
    public ResponseEntity<Authentication> login(@RequestBody AuthenticationDTO body) {
        Authentication response = authenticationService.login(body);
        return new ResponseEntity<Authentication>(response, HttpStatus.CREATED);
    }
}
