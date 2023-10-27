package com.subscription.microservice.dtos;

import com.subscription.microservice.domain.subscription.SubscriptionStatus;

public record SubscriptionDTO(String userId, Long actionId, Boolean formReceived, Boolean formResponseApproved, SubscriptionStatus status) {
}
