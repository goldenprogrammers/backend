package com.actionsMicroservice.dtos;

public record ActionDTO(long id, String title, String description, String formLink, byte[] image) {
}
