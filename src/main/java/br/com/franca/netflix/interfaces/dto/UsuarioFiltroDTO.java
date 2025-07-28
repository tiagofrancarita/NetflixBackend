package br.com.franca.netflix.interfaces.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFiltroDTO {

    private String nome;
    private Boolean ativo;

}