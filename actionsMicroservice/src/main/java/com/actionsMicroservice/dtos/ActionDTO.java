package com.actionsMicroservice.dtos;

import com.actionsMicroservice.domain.action.ActionStatus;

public record ActionDTO(String title, String description, String formLink, byte[] image, ActionStatus status) {
}
