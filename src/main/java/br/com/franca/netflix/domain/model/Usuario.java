package br.com.franca.netflix.domain.model;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private LocalDateTime dataNascimento;
    private LocalDateTime dataCriacao; // caso precise acessar
    private StatusUsuario ativo;


}