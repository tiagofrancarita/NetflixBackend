package br.com.franca.netflix.domain.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("O e-mail " + email + " já está em uso.");
    }
}