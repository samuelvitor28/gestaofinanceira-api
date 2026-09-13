package com.samuel.gestaofinanceira_api.conta.exception;

public class NomeInvalidoException extends RuntimeException {
  public NomeInvalidoException(String message) {
    super(message);
  }
}
