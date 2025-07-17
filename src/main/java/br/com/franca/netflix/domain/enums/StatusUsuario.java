package br.com.franca.netflix.domain.enums;

public enum StatusUsuario {

    A("Ativo"),
    I("Inativo");

    private final String descricao;

    StatusUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
