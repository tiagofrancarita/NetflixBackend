package br.com.franca.netflix.domain.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episodio {

    private Long id;
    private Integer numero;
    private String titulo;
    private String descricao;
    private String duracao;
    private Long temporadaId;

}