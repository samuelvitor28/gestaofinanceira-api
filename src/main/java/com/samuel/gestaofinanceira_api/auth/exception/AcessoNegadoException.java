package com.samuel.gestaofinanceira_api.auth.exception;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
}
