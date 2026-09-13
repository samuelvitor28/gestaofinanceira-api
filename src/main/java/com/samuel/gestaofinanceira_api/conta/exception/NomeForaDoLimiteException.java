package com.samuel.gestaofinanceira_api.conta.exception;

public class NomeForaDoLimiteException extends RuntimeException {
    public NomeForaDoLimiteException(String nome) { super("O nome: " + nome + " é muito longo!"); }
}
