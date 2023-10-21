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

    public static class isActiveException extends SubscriptionCreationException{
        public isActiveException() {
            super("A ação não está aceitando inscrições por não estar ativa");
        }
    }

    public static class userAlreadyRegistered extends SubscriptionCreationException{
        public userAlreadyRegistered(){super("Usuário já está inscrito nessa ação");}
    }
}
