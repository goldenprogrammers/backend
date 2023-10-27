package com.certificate.microservice.services;

import com.certificate.microservice.dtos.AcessToken;
import com.certificate.microservice.dtos.DocumentData;
import com.certificate.microservice.dtos.DocumentRequest;
import com.certificate.microservice.dtos.SignDocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class CertificateService {
    @Autowired
    private RestTemplate restTemplate;

    public String login(AcessToken token){

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("username", token.username());
        requestParams.add("password", token.password());
        requestParams.add("client_id", token.clientId());
        requestParams.add("grant_type", token.grantType());

        URI authUrl = UriComponentsBuilder.fromHttpUrl("https://h-auth.pd.tec.br")
                .path("/auth/realms/assinador/protocol/openid-connect/token")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(authUrl)
                .headers(headers)
                .body(requestParams);

        ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);

        if(response.getStatusCode().is2xxSuccessful()){
            Map responseBody = response.getBody();
            assert responseBody != null;
            return responseBody.get("access_token").toString();
        }else{
           throw new RuntimeException("Erro na autenticação");
        }
    }

    public String addDocument(DocumentRequest documentRequest, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-tenant", "6cbe14eb-fae5-4d47-b697-9128d512649e");
        headers.set("Authorization", "Bearer "+bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String idProcess = "b96f96ac-6324-419e-bb15-227872843bd4";

        HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);

        String url = "https://esign-api-pprd.portaldedocumentos.com.br/processes/" + idProcess + "/documents";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map responseBody = response.getBody();
                assert responseBody != null;
                return responseBody.get("id").toString();
            } else {
                throw new RuntimeException("Erro no Id do documento");
            }
        } catch (NullPointerException exc) {
            throw new NoSuchElementException("Processo");
        }
    }

    public SignDocumentDTO uploadDocument(byte[] document, String idDocument, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-tenant", "6cbe14eb-fae5-4d47-b697-9128d512649e");
        headers.set("Authorization", "Bearer " + bearerToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String idProcess = "b96f96ac-6324-419e-bb15-227872843bd4";

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(document, headers);
        String url = "https://esign-api-pprd.portaldedocumentos.com.br/processes/" + idProcess + "/documents/" + idDocument;

        ResponseEntity<SignDocumentDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SignDocumentDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro no envio do documento para assinatura");
        }
    }
}
