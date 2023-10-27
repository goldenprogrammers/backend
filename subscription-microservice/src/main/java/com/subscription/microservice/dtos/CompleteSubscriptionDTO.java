package com.subscription.microservice.dtos;

import com.subscription.microservice.domain.subscription.SubscriptionStatus;

public record CompleteSubscriptionDTO(String id, Long actionId, String nome, String email, Boolean formReceived, Boolean formResponseApproved, SubscriptionStatus status) {
}
