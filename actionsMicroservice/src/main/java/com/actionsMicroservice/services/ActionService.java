package com.actionsMicroservice.services;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.exceptions.ActionCreationException;
import com.actionsMicroservice.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new ActionCreationException.RequiredField("título");
        else if (data.title().length() > 80)
            throw new ActionCreationException.TitleException();

        if (data.description() == null || data.description().isEmpty())
            throw new ActionCreationException.RequiredField("descrição");
        else if (data.description().length() > 4096)
            throw new ActionCreationException.DescriptionException();

        if (data.image() == null || data.image().length == 0)
            throw new ActionCreationException.RequiredField("imagem");

        if (data.formLink() == null || data.formLink().isEmpty())
            throw new ActionCreationException.RequiredField("formLink");

        Action newAction = new Action(data);
        this.saveAction(newAction);
        return newAction;
    }
}
