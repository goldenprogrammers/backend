package com.authentication.microservice;

import com.authentication.microservice.domain.authentication.Authentication;
import com.authentication.microservice.dtos.AuthenticationDTO;
import com.authentication.microservice.exceptions.AuthenticationLoginException;
import com.authentication.microservice.services.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationServiceTests {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void login() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("hudson.borges@pantanal.com", "pantanal.dev");
        Authentication auth = new Authentication("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOcFRzWG42RUtIdWFMaVdrWWFtNjg3VkswU1c3aHRDNEZBVmhDdXpzLUZvIn0.eyJleHAiOjE2OTc3MTA4NzAsImlhdCI6MTY5NzcwMzY3MCwianRpIjoiMTk0MGEzYzMtMTJiMC00N2ExLTgwZGQtNDdkYWY1M2EyM2JmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYzA5ZjNlNjAtODAwNC00YTE4LWI5M2ItOTg3MWJkNDdiNzFiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicGFudGFuYWwtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjhkMjM1NzZmLThjODYtNGFhZS1iZDI5LWJkMmEyZjk1YWZiZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wYW50YW5hbC1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InBhbnRhbmFsLWNsaWVudCI6eyJyb2xlcyI6WyJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiI4ZDIzNTc2Zi04Yzg2LTRhYWUtYmQyOS1iZDJhMmY5NWFmYmQiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJIdWRzb24gQm9yZ2VzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiaHVkc29uLmJvcmdlcyIsImdpdmVuX25hbWUiOiJIdWRzb24iLCJmYW1pbHlfbmFtZSI6IkJvcmdlcyIsImVtYWlsIjoiaHVkc29uLmJvcmdlc0BwYW50YW5hbC5jb20ifQ.B_GEKYO_11U9btWcWN51GNh3vFCaRbuk0Ds_4m34qycjBMr0bBXcQ-_TaXUrTJEOfyeM3rOWb1FZxD82jUH80DIxoB1hpa-9MJzK9FhyX0jPg7ImV1wjtllX3D7d1sxZJGIfQ7AxpvxYY_Fi7rhooZ3xGnJzEpOpejeNmzgO-M6vANqPyvXs6I_CHIlF90IPPGrXha8O4Im3hwX4R-3PoCEzkmR9XeysWcNLJ3W9zlmKgt8wbATXXNuJjWkCDnw3UqChoxTq7VGGrnEM3p7Ck7l8wrMUmyY0FVEtgMJcZkQvfIX5EkRf6IHWLVj0NaV9TJZnyTKOEH_hwgeFxmai3A",
                7200,
                "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjMTMwNWU0Ny1jMDlkLTRlNjMtOGFlMi02OTRkYTM5ZjI5OTkifQ.eyJleHAiOjE2OTc3MDU0NzAsImlhdCI6MTY5NzcwMzY3MCwianRpIjoiOWZhYzJmZmYtNGFhZi00MDI5LTg0YzMtY2U1N2FiZDA1MWY4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvcmVhbG1zL3BhbnRhbmFsLWRldiIsInN1YiI6ImMwOWYzZTYwLTgwMDQtNGExOC1iOTNiLTk4NzFiZDQ3YjcxYiIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwYW50YW5hbC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiOGQyMzU3NmYtOGM4Ni00YWFlLWJkMjktYmQyYTJmOTVhZmJkIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiOGQyMzU3NmYtOGM4Ni00YWFlLWJkMjktYmQyYTJmOTVhZmJkIn0.Cm_DyqltM2pfj0Ztk0cNQv8Ng41nXh9V2B75OZMorxE"
        );
        System.out.println(authenticationDTO);
        Assertions.assertEquals(auth, authenticationService.login(authenticationDTO));
    }

    @Test
    void credencialFieldValidation() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(null, "pantanal.dev");
        Assertions.assertThrows(AuthenticationLoginException.RequiredField.class, () -> authenticationService.login(authenticationDTO));
    }

    @Test
    void passwordFieldValidation() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("hudson.borges@pantanal.com", null);
        Assertions.assertThrows(AuthenticationLoginException.RequiredField.class, () -> authenticationService.login(authenticationDTO));
    }

    @Test
    void badCredentialValidation() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("hudson.borges@pantanal.com", "senha123");
        Assertions.assertThrows(AuthenticationLoginException.CredentialsException.class, () -> authenticationService.login(authenticationDTO));
    }
}
