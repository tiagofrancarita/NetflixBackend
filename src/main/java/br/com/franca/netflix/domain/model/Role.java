package br.com.franca.netflix.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class Role {

    private Long id;
    private String nome;
}