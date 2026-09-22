package com.samuel.gestaofinanceira_api.auth.exception;

public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException(String message) {
        super(message);
    }
}
