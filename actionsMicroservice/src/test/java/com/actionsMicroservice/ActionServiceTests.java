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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ActionServiceTests {
    @Mock
    private ActionRepository actionRepository;
    @InjectMocks
    private ActionService actionService;

    @Test
    public void createAction() {
        ActionDTO actionDTO = new ActionDTO("título", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Action action = new Action(actionDTO);
        Assertions.assertEquals(action, actionService.createAction(actionDTO));
    }

    @Test
    public void titleValidation() {
        ActionDTO actionDTO = new ActionDTO("", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void titleLengthValidation() {
        ActionDTO actionDTO = new ActionDTO("Título".repeat(13) + "aaa", "Descrição", "www.google.com.br", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.TitleException.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void descriptionValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "", "www.google.com.br", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void descriptionLengthValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição".repeat(455) + "aa", "www.google.com.br", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.DescriptionException.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void imageValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "www.google.com.br", new byte[] {}, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }

    @Test
    public void formValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "", new byte[] { 12 }, ActionStatus.valueOf("active"));
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }
    @Test
    public void statusValidation() {
        ActionDTO actionDTO = new ActionDTO("Título", "Descrição", "www.google.com.br", new byte[] { 12 }, null);
        Assertions.assertThrows(ActionCreationException.RequiredField.class, () -> actionService.createAction(actionDTO));
    }
}
