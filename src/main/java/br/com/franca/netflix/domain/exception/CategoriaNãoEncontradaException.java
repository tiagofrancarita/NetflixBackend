package br.com.franca.netflix.domain.exception;

public class CategoriaNãoEncontradaException extends RuntimeException {
    public CategoriaNãoEncontradaException(String message) {
        super(message);
    }
}
