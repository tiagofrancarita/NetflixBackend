package br.com.franca.netflix.interfaces.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;

}
