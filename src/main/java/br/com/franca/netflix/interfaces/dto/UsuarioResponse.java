package br.com.franca.netflix.interfaces.dto;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDateTime dataNascimento;
    private LocalDateTime dataCriacao;
    private StatusUsuario ativo; // opcional

}
