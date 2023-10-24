package com.certificate.microservice.dtos;

public record AcessToken(String username, String password, String clientId, String clientSecret, String grantType) {
}
