package com.samuel.gestaofinanceira_api.conta.exception;

public class NomeNullException extends RuntimeException {
    public NomeNullException() { super("Nome não pode ser null!"); }
}
