package com.certificate.microservice.controllers;

import com.certificate.microservice.dtos.AcessToken;
import com.certificate.microservice.dtos.DocumentData;
import com.certificate.microservice.dtos.DocumentRequest;
import com.certificate.microservice.dtos.SignDocumentDTO;
import com.certificate.microservice.services.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Map;

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


    @PostMapping("/add")
    @Operation(summary = "Adicionar documento")
    public ResponseEntity<String> addDocument(@RequestBody DocumentRequest doc){
        AcessToken token = new AcessToken("time7@pantanal.dev", "nxj85J0Onk!", "assinador-app", null, "password");
        String auth = certificateService.login(token);
        String response = certificateService.addDocument(doc, auth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send/{documentName}")
    @Operation(summary = "Enviar documento")
    public ResponseEntity<SignDocumentDTO> sendDocument(
            @RequestBody Map<String, String> requestBody, @PathVariable String documentName){
        AcessToken token = new AcessToken("time7@pantanal.dev", "nxj85J0Onk!", "assinador-app", null, "password");
        String base64 = requestBody.get("file");
        byte[] fileBytes = Base64.getDecoder().decode(base64);
        DocumentRequest documentRequest = new DocumentRequest("PDF", false, documentName, 0, "SIGN");
        String bearerAuth = certificateService.login(token);

        String documentId = certificateService.addDocument(documentRequest , bearerAuth);

        SignDocumentDTO signDocumentDTO = certificateService.uploadDocument(fileBytes, documentId, bearerAuth);


        return new ResponseEntity<>(signDocumentDTO, HttpStatus.OK);
    }
}
