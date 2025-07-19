package br.com.franca.netflix.domain.model;

import br.com.franca.netflix.domain.enums.TipoMidia;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filme {

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
