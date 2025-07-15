package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.application.usecase.CadastrarUsuarioUseCase;
import br.com.franca.netflix.application.usecase.InativarUsuarioUseCase;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.interfaces.dto.MensagemResponse;
import br.com.franca.netflix.interfaces.dto.UsuarioRequest;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final InativarUsuarioUseCase inativarUsuarioUseCase;

    public UsuarioController(CadastrarUsuarioUseCase cadastrarUsuarioUseCase, InativarUsuarioUseCase inativarUsuarioUseCase) {
        this.cadastrarUsuarioUseCase = cadastrarUsuarioUseCase;
        this.inativarUsuarioUseCase = inativarUsuarioUseCase;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro bem-sucedida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(responseCode = "400", description = "Erro no cadastro")
    })
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRequest request) {
        Usuario novo = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .cpf(request.getCpf())
                .dataNascimento(request.getDataNascimento())
                .ativo(request.getAtivo())
                .build();

        Usuario salvo = cadastrarUsuarioUseCase.executar(novo);

        UsuarioResponse usuarioresponse = new UsuarioResponse();
        usuarioresponse.setId(salvo.getId());
        usuarioresponse.setNome(salvo.getNome());
        usuarioresponse.setEmail(salvo.getEmail());
        usuarioresponse.setCpf(salvo.getCpf());
        usuarioresponse.setDataNascimento(salvo.getDataNascimento());
        usuarioresponse.setDataCriacao(salvo.getDataCriacao());
        usuarioresponse.setAtivo(salvo.getAtivo());

        return ResponseEntity.ok(usuarioresponse);
    }

    @PutMapping("/{email}/inativarEmail")
    @Operation(summary = "Inativa um usuário pelo e-mail")
    public ResponseEntity<MensagemResponse> inativarEmail(@PathVariable String email) {
        inativarUsuarioUseCase.executar(email);
        return ResponseEntity.ok(new MensagemResponse("Usuário inativado com sucesso."));
    }

    @PutMapping("/{id}/inativarPorId")
    @Operation(summary = "Inativa um usuário pelo id")
    public ResponseEntity<Void> inativarPorId(@PathVariable Long id) {
        inativarUsuarioUseCase.inativarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpf}/inativarPorCpf")
    @Operation(summary = "Inativa um usuário pelo cpf")
    public ResponseEntity<Void> inativarPorCpf(@PathVariable String cpf) {
        inativarUsuarioUseCase.inativarPorCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}