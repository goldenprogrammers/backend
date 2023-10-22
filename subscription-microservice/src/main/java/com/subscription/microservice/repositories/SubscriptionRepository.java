package com.subscription.microservice.repositories;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.domain.subscription.SubscriptionId;
import com.subscription.microservice.dtos.SubscriptionIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId>{
    Optional<Subscription> findByUserIdAndActionId(String userId, Long actionId);
    Page<Subscription> findAllByActionId(Long id, Pageable pageable);
    Subscription findAllSubscriotionByActionId(Long id);
}
