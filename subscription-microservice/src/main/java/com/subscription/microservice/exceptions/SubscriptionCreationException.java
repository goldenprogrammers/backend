package com.subscription.microservice.exceptions;

import com.subscription.microservice.domain.subscription.Subscription;

public class SubscriptionCreationException extends RuntimeException{

    public SubscriptionCreationException(String message){
        super(message);
    }

    public static class RequiredField extends SubscriptionCreationException{
        public RequiredField(String field){
            super("O campo " + field + "é obrigatório");
        }
    }
}
