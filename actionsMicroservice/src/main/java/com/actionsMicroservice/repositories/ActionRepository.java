package com.actionsMicroservice.repositories;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.domain.action.ActionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Page<Action> findByTitleContainingAndStatus(String title, ActionStatus status, PageRequest pagination);
}
