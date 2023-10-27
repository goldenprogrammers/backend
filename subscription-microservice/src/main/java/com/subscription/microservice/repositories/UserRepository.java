package com.subscription.microservice.repositories;

import com.subscription.microservice.domain.subscription.User;
import com.subscription.microservice.dtos.CompleteSubscriptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, CompleteSubscriptionDTO> {
    Optional<User> findById(String userId);
}
