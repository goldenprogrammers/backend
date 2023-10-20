package com.subscription.microservice.services;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.dtos.SubscriptionDTO;
import com.subscription.microservice.exceptions.SubscriptionCreationException;
import com.subscription.microservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    public void saveSubscription(Subscription subscription){
        this.repository.save(subscription);
    }

    //TODO: Adicionar verificação se a ação está ativa
    public Subscription createSubscription(SubscriptionDTO subscription){
        if(subscription.userId() == null)
            throw new SubscriptionCreationException.RequiredField("userId");
        if(subscription.actionId() == null)
            throw  new SubscriptionCreationException.RequiredField("actionId");

        Subscription newSubscription = new Subscription(subscription);
        this.saveSubscription(newSubscription);
        return newSubscription;
    }
}
