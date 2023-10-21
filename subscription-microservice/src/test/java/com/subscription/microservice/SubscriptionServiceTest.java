package com.subscription.microservice;
import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.domain.subscription.SubscriptionStatus;
import com.subscription.microservice.dtos.SubscriptionDTO;
import com.subscription.microservice.dtos.SubscriptionIdDTO;
import com.subscription.microservice.exceptions.SubscriptionCreationException;
import com.subscription.microservice.repositories.SubscriptionRepository;
import com.subscription.microservice.services.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository mockRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Defina quaisquer configurações de mock ou comportamento padrão aqui
    }

    @Test
    public void createSubscription() {
        SubscriptionIdDTO subscriptionIdDTO = new SubscriptionIdDTO("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOcFRzWG42RUtIdWFMaVdrWWFtNjg3VkswU1c3aHRDNEZBVmhDdXpzLUZvIn0.eyJleHAiOjE2OTc3MTA4NzAsImlhdCI6MTY5NzcwMzY3MCwianRpIjoiMTk0MGEzYzMtMTJiMC00N2ExLTgwZGQtNDdkYWY1M2EyM2JmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYzA5ZjNlNjAtODAwNC00YTE4LWI5M2ItOTg3MWJkNDdiNzFiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicGFudGFuYWwtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjhkMjM1NzZmLThjODYtNGFhZS1iZDI5LWJkMmEyZjk1YWZiZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wYW50YW5hbC1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InBhbnRhbmFsLWNsaWVudCI6eyJyb2xlcyI6WyJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiI4ZDIzNTc2Zi04Yzg2LTRhYWUtYmQyOS1iZDJhMmY5NWFmYmQiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJIdWRzb24gQm9yZ2VzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiaHVkc29uLmJvcmdlcyIsImdpdmVuX25hbWUiOiJIdWRzb24iLCJmYW1pbHlfbmFtZSI6IkJvcmdlcyIsImVtYWlsIjoiaHVkc29uLmJvcmdlc0BwYW50YW5hbC5jb20ifQ.B_GEKYO_11U9btWcWN51GNh3vFCaRbuk0Ds_4m34qycjBMr0bBXcQ-_TaXUrTJEOfyeM3rOWb1FZxD82jUH80DIxoB1hpa-9MJzK9FhyX0jPg7ImV1wjtllX3D7d1sxZJGIfQ7AxpvxYY_Fi7rhooZ3xGnJzEpOpejeNmzgO-M6vANqPyvXs6I_CHIlF90IPPGrXha8O4Im3hwX4R-3PoCEzkmR9XeysWcNLJ3W9zlmKgt8wbATXXNuJjWkCDnw3UqChoxTq7VGGrnEM3p7Ck7l8wrMUmyY0FVEtgMJcZkQvfIX5EkRf6IHWLVj0NaV9TJZnyTKOEH_hwgeFxmai3A", 1L);

        when(mockRepository.findByUserIdAndActionId("c09f3e60-8004-4a18-b93b-9871bd47b71b", 1L)).thenReturn(Optional.empty());

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(Collections.singletonMap("isActive", true), HttpStatus.OK);
        when(restTemplate.getForEntity(eq("http://localhost:8080/action/isactive/1"), eq(Map.class))).thenReturn(responseEntity);

        Subscription createdSubscription = subscriptionService.createSubscription(subscriptionIdDTO);

        verify(mockRepository, times(1)).save(any(Subscription.class));

        assertEquals("c09f3e60-8004-4a18-b93b-9871bd47b71b", createdSubscription.getUserId());
        assertEquals(1L, createdSubscription.getActionId());

    }

    @Test
    public void testUpdateSubscription() {
        // Dados de exemplo
        SubscriptionIdDTO subscriptionId = new SubscriptionIdDTO("userId123", 1L);
        SubscriptionDTO updatedSubscription = new SubscriptionDTO("userId123", 1L, true, true, SubscriptionStatus.APPROVED);

        Subscription oldSubscription = new Subscription();
        oldSubscription.setUserId("userId123");
        oldSubscription.setActionId(1L);
        oldSubscription.setFormReceived(false);
        oldSubscription.setFormResponseApproved(false);
        oldSubscription.setStatus(SubscriptionStatus.IN_PROGRESS);

        // Simule o comportamento do repositório
        when(mockRepository.findByUserIdAndActionId(subscriptionId.userId(), subscriptionId.actionId()))
                .thenReturn(Optional.of(oldSubscription));

        // Execute o método a ser testado
        Subscription result = subscriptionService.updateSubscription(subscriptionId, updatedSubscription);

        // Verifique se o método do repositório foi chamado corretamente
        verify(mockRepository, times(1)).findByUserIdAndActionId(subscriptionId.userId(), subscriptionId.actionId());

        // Verifique se o resultado é o esperado
        assertEquals(updatedSubscription.formReceived(), result.getFormReceived());
        assertEquals(updatedSubscription.formResponseApproved(), result.getFormResponseApproved());
        assertEquals(updatedSubscription.status(), result.getStatus());
    }
}

