package br.com.franca.netflix.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UsuarioRequest {

    @NotBlank(message = "O nome é obrigatório!")
    private String nome;

    @Email(message = "E-mail inválido!")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    @Size(min = 8, message = "A senha deve ter no minímo 8 caracteres")
    private String senha;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDateTime dataNascimento;
}
