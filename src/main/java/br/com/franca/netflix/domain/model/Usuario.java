package br.com.franca.netflix.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;

}