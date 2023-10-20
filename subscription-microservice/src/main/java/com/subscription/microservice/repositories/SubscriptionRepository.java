package com.subscription.microservice.repositories;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.domain.subscription.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId>{
}
