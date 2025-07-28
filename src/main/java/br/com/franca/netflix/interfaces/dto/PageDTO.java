package br.com.franca.netflix.interfaces.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private int paginaAtual;
    private int totalPaginas;
    private long totalElementos;

    public PageDTO(List<T> content, int paginaAtual, int totalPaginas, long totalElementos) {
        this.content = content;
        this.paginaAtual = paginaAtual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
    }
}