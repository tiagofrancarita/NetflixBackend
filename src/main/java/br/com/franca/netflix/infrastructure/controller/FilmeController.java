package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.application.usecase.*;
import br.com.franca.netflix.interfaces.dto.FilmeAtualizacaoDTO;
import br.com.franca.netflix.interfaces.dto.FilmeRequestDTO;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final CadastrarFilmeUseCase cadastrarFilmeUseCase;
    private final BuscarFilmeUseCase buscarFilmeUseCase;
    private final ListarFilmesUseCase listarFilmesUseCase;
    private final AtualizarFilmeUseCase atualizarFilmeUseCase;
    private final DeletarFilmeUseCase deletarFilmeUseCase;

    String mensagemCadastroComSucesso = "Filme cadastrado com sucesso.";
    String mensagemCadastroComErro = "Erro ao cadastrar o filme.";
    String mensagemDeletadoComSucesso = "Filme com ID " + " removido com sucesso";

    public FilmeController(CadastrarFilmeUseCase cadastrarFilmeUseCase,
                           BuscarFilmeUseCase buscarFilmeUseCase,
                           ListarFilmesUseCase listarFilmesUseCase,
                           AtualizarFilmeUseCase atualizarFilmeUseCase,
                           DeletarFilmeUseCase deletarFilmeUseCase) {
        this.cadastrarFilmeUseCase = cadastrarFilmeUseCase;
        this.buscarFilmeUseCase = buscarFilmeUseCase;
        this.listarFilmesUseCase = listarFilmesUseCase;
        this.atualizarFilmeUseCase = atualizarFilmeUseCase;
        this.deletarFilmeUseCase = deletarFilmeUseCase;
    }

    @GetMapping("/listarFilmes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Listar todos os filmes e séries")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme/Série encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario(a) não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme/Série não encontrado")
    })
    public ResponseEntity<List<FilmeResponseDTO>> listarFilmes() {
        return ResponseEntity.ok(listarFilmesUseCase.listarTodos());
    }

    @GetMapping("/buscarFilmePorID/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Buscar filme ou série por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme/Série encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario(a) não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme/Série não encontrado")
    })
    public ResponseEntity<FilmeResponseDTO> buscarFilmePorID(@PathVariable Long id) {
        return ResponseEntity.ok(buscarFilmeUseCase.buscarPorId(id));
    }

    @PostMapping("/cadastrarFilme")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar um novo filme ou série")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Filme/Série criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<FilmeResponseDTO> cadastrarFilme(@RequestBody @Valid FilmeRequestDTO requestDTO) {
        FilmeResponseDTO criado = cadastrarFilmeUseCase.executar(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/atualizarFilmePorID/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar um filme ou série existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme/Série não encontrado")
    })
    public ResponseEntity<FilmeResponseDTO> atualizarFilmePorId(@PathVariable Long id, @RequestBody FilmeAtualizacaoDTO filmeAtualizacaoDTO) {

        FilmeResponseDTO atualizado = atualizarFilmeUseCase.executar(id, filmeAtualizacaoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletarFilmePorID/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover um filme ou série")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme/Série não encontrado")
    })
    public ResponseEntity<?> deletarFilmePorID(@PathVariable Long id) {
        deletarFilmeUseCase.executar(id);

        return ResponseEntity.ok().body(Map.of("mensagem", mensagemDeletadoComSucesso + id));
    }
}