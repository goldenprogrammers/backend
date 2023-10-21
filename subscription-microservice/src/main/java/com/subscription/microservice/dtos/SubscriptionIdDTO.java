package com.subscription.microservice.dtos;

import lombok.*;


public record SubscriptionIdDTO(String userId, Long actionId) {
}
