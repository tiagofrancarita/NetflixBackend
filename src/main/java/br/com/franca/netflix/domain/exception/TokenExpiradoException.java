package br.com.franca.netflix.domain.exception;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
}
