package com.authentication.microservice.exceptions;

public class AuthenticationLoginException extends RuntimeException {

    public AuthenticationLoginException(String message) { super(message); }

    public static class CredentialsException extends  AuthenticationLoginException {
        public CredentialsException() { super("Usuário ou senha incorretos.");}
    }

    public static class RequiredField extends AuthenticationLoginException {
        public RequiredField(String field) {
            super("O campo " + field + " é obrigatório.");
        }
    }
}
