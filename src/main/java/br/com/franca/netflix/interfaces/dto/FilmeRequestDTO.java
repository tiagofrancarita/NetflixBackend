package br.com.franca.netflix.interfaces.dto;

import br.com.franca.netflix.domain.enums.TipoMidia;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Tipo de mídia é obrigatório")
    private TipoMidia midia;

    @NotNull(message = "Ano de lançamento é obrigatório")
    @Min(value = 1900, message = "Ano de lançamento deve ser maior ou igual a 1900")
    private Integer ano;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;

    @NotNull(message = "URL da imagem é obrigatória")
    private String imagemUrl;

    @NotNull(message = "URL da video é obrigatória")
    private String videoUrl;

    @NotNull(message = "Duração do video é obrigatória")
    private String duracao;

}