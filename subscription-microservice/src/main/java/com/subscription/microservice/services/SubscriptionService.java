package com.subscription.microservice.services;

import com.subscription.microservice.domain.subscription.Subscription;
import com.subscription.microservice.domain.subscription.SubscriptionId;
import com.subscription.microservice.domain.subscription.SubscriptionStatus;
import com.subscription.microservice.dtos.SubscriptionDTO;
import com.subscription.microservice.dtos.SubscriptionIdDTO;
import com.subscription.microservice.exceptions.SubscriptionCreationException;
import com.subscription.microservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void saveSubscription(Subscription subscription){
        this.repository.save(subscription);
    }

    public Subscription createSubscription(SubscriptionIdDTO subscription){
        if(subscription.userId() == null)
            throw new SubscriptionCreationException.RequiredField("userId");

        if(subscription.actionId() == null)
            throw  new SubscriptionCreationException.RequiredField("actionId");

        String subToken = convertJwt(subscription.userId());

        Optional<Subscription> searchedSubcription = this.repository.findByUserIdAndActionId(subToken, subscription.actionId());
        if(searchedSubcription.isPresent()){
            throw new SubscriptionCreationException.userAlreadyRegistered();
        }
        System.out.println(subscription.actionId());
        try {
            ResponseEntity<Map> responseAction = restTemplate.getForEntity("https://actions-forcaesperanca.up.railway.app/action/isactive/" + subscription.actionId(), Map.class);
            if (responseAction.getStatusCode() == HttpStatus.OK && responseAction.getBody().get("isActive") == Boolean.FALSE) {
                throw new SubscriptionCreationException.isActiveException();
            }
        }catch (HttpClientErrorException exc){
            throw new NoSuchElementException("Ação");
        }

        SubscriptionIdDTO updateSubscription = new SubscriptionIdDTO(subToken, subscription.actionId());
        Subscription newSubscription = new Subscription(updateSubscription);
        this.saveSubscription(newSubscription);
        return newSubscription;
    }

    public Page<Subscription> getSubscriptionByAction(int page, int pageSize, Long actionId){
        PageRequest pagination = this.getPagination(page, pageSize);
        return this.repository.findAllByActionId(actionId, pagination);
    }

    public PageRequest getPagination(int page, int pageSize){
        return PageRequest.of(page, pageSize);
    }

    public Subscription updateSubscription(SubscriptionIdDTO subscriptionId, SubscriptionDTO updatedSubscription){

        Subscription oldSubscription = getSubscriptionByid(subscriptionId);

        if (updatedSubscription.formReceived() == Boolean.TRUE) {
            oldSubscription.setFormReceived(Boolean.TRUE);
        }
        if(updatedSubscription.formResponseApproved() == Boolean.TRUE){
            oldSubscription.setFormResponseApproved(Boolean.TRUE);
        }
        if(updatedSubscription.status() != SubscriptionStatus.IN_PROGRESS){
            oldSubscription.setStatus(updatedSubscription.status());
        }
        this.saveSubscription(oldSubscription);
        return oldSubscription;
    }

    public Subscription getSubscriptionByid(SubscriptionIdDTO subscriptionId){
        Optional<Subscription> subscription = this.repository.findByUserIdAndActionId(subscriptionId.userId(), subscriptionId.actionId());
        if(subscription.isPresent()){
            return subscription.get();
        }
        throw new NoSuchElementException("Inscrição");
    }
    public String convertJwt(String authorization){
        // Divide o JWT em três partes com base no caractere de ponto (.)
        String[] parts = authorization.split("\\.");
        String sub = null;
        if (parts.length == 3) {
            String payload = parts[1];

            // Decodifica a parte do payload (base64)
            byte[] decodedBytes = Base64.getDecoder().decode(payload);
            String decodedPayload = new String(decodedBytes);

            // Analise o payload decodificado como um JSON
            // Exemplo usando a biblioteca JSON simples (JSON simple)
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            try {
                // Converte o payload decodificado em um mapa de chave-valor
                Map<String, Object> payloadMap = (Map<String, Object>) parser.parse(decodedPayload);

                // Exemplo de extração do campo "sub" do JWT
                sub = (String) payloadMap.get("sub");

            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
        return sub;
    }



}
