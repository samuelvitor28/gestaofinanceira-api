package com.samuel.gestaofinanceira_api.usuario.exception;

public class UsernameJaCadastradoException extends RuntimeException {
    public UsernameJaCadastradoException(String username) {
        super("Username já cadastrado: " + username);
    }
}