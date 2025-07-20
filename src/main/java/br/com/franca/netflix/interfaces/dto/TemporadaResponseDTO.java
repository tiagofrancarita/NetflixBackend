package br.com.franca.netflix.interfaces.dto;

import br.com.franca.netflix.domain.model.Episodio;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporadaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer numero;
    private Long serieId;
    private List<EpisodioResponseDTO> episodios;

}
