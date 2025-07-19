package br.com.franca.netflix.interfaces.dto;

import br.com.franca.netflix.domain.enums.TipoMidia;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmeResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private TipoMidia midia;
    private String imagemUrl;
    private String videoUrl;
    private Integer ano;
    private String duracao;
    private Long categoriaId;

}
