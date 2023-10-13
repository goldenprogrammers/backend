package com.actionsMicroservice;

import com.actionsMicroservice.domain.action.Action;
import com.actionsMicroservice.domain.action.ActionStatus;
import com.actionsMicroservice.dtos.ActionDTO;
import com.actionsMicroservice.exceptions.ActionCreationException;
import com.actionsMicroservice.repositories.ActionRepository;
import com.actionsMicroservice.services.ActionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class ActionServiceTests {
    @Mock
    private ActionRepository mockRepository;

    @InjectMocks
    private ActionService actionService;

    @Test
    public void createAction() {
        ActionDTO actionDTO = new ActionDTO("título", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.active);
        Action action = new Action(actionDTO);
        Mockito.when(mockRepository.save(action)).thenReturn(action);
        Assertions.assertEquals(action, actionService.createAction(actionDTO));
    }

    @Test
    public void titleValidation() {
        ActionDTO actionDTO = new ActionDTO("", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void titleLengthValidation() {
        ActionDTO actionDTO = new ActionDTO("Título".repeat(13) + "aaa", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.TitleException.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void descriptionValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "", "www.google.com.br", new byte[] { 12 }, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void descriptionLengthValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição".repeat(455) + "aa", "www.google.com.br", new byte[] { 12 }, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.DescriptionException.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void imageValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "www.google.com.br", new byte[] {}, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void formValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "", new byte[] { 12 }, ActionStatus.active);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }
    @Test
    public void statusValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "www.google.com.br", new byte[] { 12 }, null);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void findById() {
        Action action = new Action((long) 1, "título", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.active, Instant.now());
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(action));
        Assertions.assertEquals(action, actionService.getActionById(1));
    }

    @Test
    public void findByIdException() {
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> actionService.getActionById(1));
    }

    @Test
    public void getPaginationWithoutSort() {
        PageRequest pagination = PageRequest.of(1, 25);
        Assertions.assertEquals(pagination, actionService.getPagination(1, 25, null));
    }

    @Test
    public void getPaginationWithAscSort() {
        Sort sortMethod = Sort.by(Sort.Order.asc("timestamp"));
        PageRequest pagination = PageRequest.of(1, 25, sortMethod);
        Assertions.assertEquals(pagination, actionService.getPagination(1, 25, Sort.Direction.ASC));
    }

    @Test
    public void getPaginationWithDescSort() {
        Sort sortMethod = Sort.by(Sort.Order.desc("timestamp"));
        PageRequest pagination = PageRequest.of(1, 25, sortMethod);
        Assertions.assertEquals(pagination, actionService.getPagination(1, 25, Sort.Direction.DESC));
    }
}
