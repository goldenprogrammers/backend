package com.subscriptionMicroservice.repositories;

import com.subscriptionMicroservice.domain.subscription.Subscription;
import com.subscriptionMicroservice.domain.subscription.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId>{
}
