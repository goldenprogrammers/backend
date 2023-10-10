package com.actionsMicroservice.repositories;

import com.actionsMicroservice.domain.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
