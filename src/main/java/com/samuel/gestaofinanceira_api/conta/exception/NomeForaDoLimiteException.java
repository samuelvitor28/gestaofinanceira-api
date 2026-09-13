package com.samuel.gestaofinanceira_api.conta.exception;

public class NomeForaDoLimiteException extends RuntimeException {
  public NomeForaDoLimiteException(String message) {
    super(message);
  }
}
