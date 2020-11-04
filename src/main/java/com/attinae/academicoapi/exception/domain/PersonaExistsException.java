package com.attinae.academicoapi.exception.domain;

public class PersonaExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersonaExistsException(String message) {
        super(message);
    }
}
