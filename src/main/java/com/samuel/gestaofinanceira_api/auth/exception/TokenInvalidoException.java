package com.samuel.gestaofinanceira_api.auth.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
