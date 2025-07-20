package br.com.franca.netflix.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodioResponseDTO {

    private Long id;
    private String titulo;
    private Integer numero;
    private String descricao;
    private Long temporadaId;

}