package com.subscription.microservice;

import com.subscription.microservice.repositories.SubscriptionRepository;
import com.subscription.microservice.services.SubscriptionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubscriptionServiceTests {
    @Mock
    private SubscriptionRepository mockRepository;
    @InjectMocks
    private SubscriptionService subscriptionService;

//    @Test
//    void createSubscription(){
//        SubscriptionDTO subscriptionDTO = new SubscriptionDTO(1L, 1L, false, false, SubscriptionStatus.IN_PROGRESS);
//        Subscription subscription = new Subscription(subscriptionDTO);
//        Mockito.when(mockRepository.save(subscription)).thenReturn(subscription);
//        Assertions.assertEquals(subscription, subscriptionService.createSubscription(subscriptionDTO));
//    }
}
