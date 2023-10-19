package com.authentication.microservice.dtos;

import java.sql.Timestamp;

public record AuthenticationDTO(String credential, String password) {
}
