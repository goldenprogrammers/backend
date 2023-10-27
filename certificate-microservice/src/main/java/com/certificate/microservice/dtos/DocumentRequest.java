package com.certificate.microservice.dtos;

public record DocumentRequest(String extension, Boolean isPendency, String name, int order, String type){}
