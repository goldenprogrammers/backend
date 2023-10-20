package com.subscription.microservice.dtos;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.domain.subscription.SubscriptionStatus;

public record SubscriptionDTO(Long userId, Long actionId, Boolean formReceived, Boolean formResponseApproved, SubscriptionStatus status) {
}
