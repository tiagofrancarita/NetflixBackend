package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.application.usecase.CadastrarUsuarioUseCase;
import br.com.franca.netflix.application.usecase.InativarUsuarioUseCase;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.interfaces.dto.MensagemResponse;
import br.com.franca.netflix.interfaces.dto.UsuarioRequest;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
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
    public ResponseEntity<MensagemResponse> inativarEmail(@PathVariable String email) {
        inativarUsuarioUseCase.executar(email);
        return ResponseEntity.ok(new MensagemResponse("Usuário inativado com sucesso."));
    }

    @PutMapping("/{id}/inativarPorId")
    public ResponseEntity<Void> inativarPorId(@PathVariable Long id) {
        inativarUsuarioUseCase.inativarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpf}/inativarPorCpf")
    public ResponseEntity<Void> inativarPorCpf(@PathVariable String cpf) {
        inativarUsuarioUseCase.inativarPorCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}