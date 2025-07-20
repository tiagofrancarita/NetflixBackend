package br.com.franca.netflix.interfaces.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporadaRequestDTO {

    private Integer numero;
    private Long serieId;
    private List<EpisodioRequestDTO> episodios;

}
