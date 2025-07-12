package br.com.franca.netflix.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


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
}
