package br.com.franca.netflix.interfaces.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
}