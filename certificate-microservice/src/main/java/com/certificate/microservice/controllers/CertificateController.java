package com.certificate.microservice.controllers;

import com.certificate.microservice.dtos.AcessToken;
import com.certificate.microservice.services.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@CrossOrigin(origins = "*")
@Tag(name = "Certificados", description = "Todos os endpoints de certificados")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping
    @Operation(summary = "Login")
    public ResponseEntity<String> doLogin(@RequestBody AcessToken token){
        String response = certificateService.login(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
