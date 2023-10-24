package com.certificate.microservice.services;

import com.certificate.microservice.dtos.AcessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

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
            return responseBody.get("access_token").toString();
        }else{
//            TODO: Fazer o tratamento de erro
           throw new RuntimeException("Erro na autenticação");
        }
    }
}
