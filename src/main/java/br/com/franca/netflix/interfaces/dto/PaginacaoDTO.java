package br.com.franca.netflix.interfaces.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginacaoDTO {

    @Min(0)
    private int pagina = 0;

    @Min(1)
    private int tamanho = 10;

    private String ordenacao = "id"; // campo padrão para ordenação
    private String direcao = "asc"; // asc ou desc

}
