package com.authentication.microservice.services;

import com.authentication.microservice.domain.authentication.Authentication;
import com.authentication.microservice.dtos.AuthenticationDTO;
import com.authentication.microservice.exceptions.AuthenticationLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${KEYCLOAK_BASE_URL:http://localhost:8081}")
    private String keycloakBaseUrl;

    @Value("${KEYCLOAK_REALM:pantanal-dev}")
    private String keycloakRealm;

    @Value("${KEYCLOAK_CLIENT:pantanal-client}")
    private String keycloakClient;

    public Authentication login(AuthenticationDTO data) {
        try {
            // Fields validations
            if (data.credential() == null || data.credential().isEmpty())
                throw new AuthenticationLoginException.RequiredField("credential");

            if (data.password() == null || data.password().isEmpty())
                throw new AuthenticationLoginException.RequiredField("password");

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "password");
            formData.add("password", data.password());
            formData.add("username", data.credential());
            formData.add("client_id", keycloakClient);

            HttpEntity<MultiValueMap<String, String>> reqBody = new HttpEntity<>(formData, header);
            String url = keycloakBaseUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

            ResponseEntity<Map> response = restTemplate.postForEntity(url, reqBody, Map.class);
            return new Authentication(
                    (String) response.getBody().get("access_token"),
                    ((Integer) response.getBody().get("expires_in")).longValue(),
                    (String) response.getBody().get("refresh_token")
            );
        }
        catch (HttpClientErrorException error) {
            if (error.getStatusCode() == HttpStatus.UNAUTHORIZED)
                throw new AuthenticationLoginException.CredentialsException();

            throw error;
        }
    }
}
