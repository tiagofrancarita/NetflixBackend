package br.com.franca.netflix.domain.enums;

public enum TipoMidia {

    FILME("Filme"),
    SERIE("Serie");

    private final String descricao;

    TipoMidia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}