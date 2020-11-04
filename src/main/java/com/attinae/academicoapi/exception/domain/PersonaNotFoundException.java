package com.attinae.academicoapi.exception.domain;

public class PersonaNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    public PersonaNotFoundException(String message) {
        super(message);
    }
}
