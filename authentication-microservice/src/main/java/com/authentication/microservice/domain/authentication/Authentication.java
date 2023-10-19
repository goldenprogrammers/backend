package com.authentication.microservice.domain.authentication;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

@Getter
@Setter
@EqualsAndHashCode
public class Authentication {
    private String accessToken;
    private long expiresIn;
    private String refreshToken;

    public Authentication(String accessToken, long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = Instant.now().atZone(ZoneId.of("GMT-3")).plusSeconds(expiresIn).toInstant().getEpochSecond();
        this.refreshToken = refreshToken;
    }
}
