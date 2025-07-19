package br.com.franca.netflix.domain.exception;

public class CategoriaJaCadastradaException extends RuntimeException {

    public CategoriaJaCadastradaException(String categorias) {
        super("Categoria:" + categorias + " já está em uso.");
    }
}