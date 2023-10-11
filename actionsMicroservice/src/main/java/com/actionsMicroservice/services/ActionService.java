package com.actionsMicroservice.services;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.exceptions.ActionCreationException;
import com.actionsMicroservice.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ActionService {
    @Autowired
    private ActionRepository repository;

    public void saveAction(Action action) {
        this.repository.save(action);
    }

    public Action createAction(ActionDTO data) {
        // Fields validations
        if (data.title() == null || data.title().isEmpty())
            throw new ActionCreationException.RequiredField("title");
        else if (data.title().length() > 80)
            throw new ActionCreationException.TitleException();

        if (data.description() == null || data.description().isEmpty())
            throw new ActionCreationException.RequiredField("description");
        else if (data.description().length() > 4096)
            throw new ActionCreationException.DescriptionException();

        if (data.image() == null || data.image().length == 0)
            throw new ActionCreationException.RequiredField("image");

        if (data.formLink() == null || data.formLink().isEmpty())
            throw new ActionCreationException.RequiredField("formLink");

        if (data.status() == null)
            throw new ActionCreationException.RequiredField("status");

        Action newAction = new Action(data);
        this.saveAction(newAction);
        return newAction;
    }

    public Action getActionById(long id) {
        Optional<Action> action = this.repository.findById(id);

        if (action.isPresent())
            return action.get();

        throw new NoSuchElementException("Ação");
    }
}
