package br.com.franca.netflix.interfaces.dto;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AtualizarUsuarioRequest{

    private String nome;
    private String email;
    private String senha;
    private StatusUsuario status;
    private String cpf;
    private LocalDateTime dataNascimento;

}