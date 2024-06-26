package com.actionsMicroservice.services;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.domain.action.ActionStatus;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.exceptions.ActionCreationException;
import com.actionsMicroservice.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Base64;
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

        if (data.image() == null || data.image().isEmpty())
            throw new ActionCreationException.RequiredField("image");

        byte[] decodedBytes = Base64.getDecoder().decode(data.image());
        int sizeInBytes = decodedBytes.length;
        if (sizeInBytes > 2 * 1024 * 1024)
            throw new ActionCreationException.ImageSizeException();

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

        if (action.isPresent() && !action.get().getIsDeleted())
            return action.get();

        throw new NoSuchElementException("Ação");
    }

    public Page<Action> getActions(int page, int pageSize, Sort.Direction sort, ActionStatus status) {
        Action filter = new Action();
        filter.setTimestamp(null);
        filter.setIsDeleted(Boolean.FALSE);
        PageRequest pagination = this.getPagination(page, pageSize, sort);

        if (status != null)
            filter.setStatus(status);

        return this.repository.findAll(Example.of(filter), pagination);
    }

    public Page<Action> getActionByTitle(int page, int pageSize, Sort.Direction sort, String title) {
        PageRequest pagination = this.getPagination(page, pageSize, sort);
        return this.repository.findByTitleContainingIgnoreCaseAndStatusAndIsDeleted(title, ActionStatus.active, Boolean.FALSE ,pagination);
    }

    public PageRequest getPagination(int page, int pageSize, Sort.Direction sort) {
        PageRequest pagination;

        if (sort == null)
            pagination = PageRequest.of(page, pageSize);
        else {
            Sort sortMethod = Sort.by(new Sort.Order(sort, "timestamp"));
            pagination = PageRequest.of(page, pageSize, sortMethod);
        }

        return pagination;
    }

    public void removeAction(long id){
        Action action = getActionById(id);
        action.setIsDeleted(Boolean.TRUE);
    }

    public Action updateAction(Long id, ActionDTO updatedAction){
        Action oldAction = getActionById(id);

        if(updatedAction.title() != null){
            oldAction.setTitle(updatedAction.title());
        }
        if(updatedAction.description() != null){
            oldAction.setDescription(updatedAction.description());
        }
        if(updatedAction.formLink() != null){
            throw new UnsupportedOperationException("A atualização do campo 'formLink' não é permitida.");
        }
        if (updatedAction.image() != null){
            oldAction.setImage(updatedAction.image());
        }
        if(updatedAction.status() != null){
            oldAction.setStatus(updatedAction.status());
        }
        this.saveAction(oldAction);
        return oldAction;
    }

    public Boolean activeAction(Long id){
        Action action = this.getActionById(id);
        if(action.getStatus() != ActionStatus.active) return Boolean.FALSE;
        return Boolean.TRUE;
    }
}
