package br.com.franca.netflix.domain.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temporada {

    private Long id;
    private Integer numero;
    private String titulo;
    private String descricao;
    private Long serieId; // filme_id onde tipoMidia = SERIE

}
