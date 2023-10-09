package com.actionsMicroservice.controllers;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.services.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping
    public ResponseEntity<Action> createAction(@RequestBody ActionDTO action) {
        Action newAction = actionService.createAction(action);
        return new ResponseEntity<>(newAction, HttpStatus.CREATED);
    }
}
