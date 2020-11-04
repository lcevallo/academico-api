package com.attinae.academicoapi.exception.domain;

public class UsuarioNotFoundException extends Exception{

 
    private static final long serialVersionUID = 1L;

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
