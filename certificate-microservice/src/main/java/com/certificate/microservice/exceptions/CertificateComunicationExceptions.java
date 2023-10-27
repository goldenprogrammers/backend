package com.certificate.microservice.exceptions;

public class CertificateComunicationExceptions extends RuntimeException{

    public CertificateComunicationExceptions(String message) {super(message);}

    public static class exceptionUnathorized extends CertificateComunicationExceptions{

        public exceptionUnathorized(){super("Ação do usuário não autorizada");}
    }
}
