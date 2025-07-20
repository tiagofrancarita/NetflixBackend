package br.com.franca.netflix.interfaces.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodioRequestDTO {

    private String titulo;
    private Integer numero;
    private String descricao;
    private Long temporadaId;

}
