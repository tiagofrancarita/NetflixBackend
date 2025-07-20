package br.com.franca.netflix.interfaces.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporadaRequestDTO {

    private Long id;
    private Integer numero;
    private String titulo;
    private String descricao;
    private Long serieId;
    private List<EpisodioRequestDTO> episodios;

}
