package br.com.franca.netflix.domain.exception;

public class RecursoDuplicadoException extends RuntimeException {
    public RecursoDuplicadoException(String campo, String valor) {
        super("Já existe um registro com o " + campo + ": " + valor);
    }
}
