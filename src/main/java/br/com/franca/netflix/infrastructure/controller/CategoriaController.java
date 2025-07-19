package br.com.franca.netflix.infrastructure.controller;


import br.com.franca.netflix.application.usecase.*;
import br.com.franca.netflix.interfaces.dto.AtualizarCategoriaRequestDTO;
import br.com.franca.netflix.interfaces.dto.CategoriaRequestDTO;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
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

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final DeletarCategoriaUseCase deletarCategoriaUseCase;
    private final CadastrarCategoriaUseCase cadastrarCategoriaUseCase;
    private final ListarCategoriasUseCase listarCategoriasUseCase;
    private final BuscarCategoriaUseCase buscarCategoriaUseCase;
    private final AtualizarCategoriaUseCase atualizarCategoriaUseCase;

    public CategoriaController(DeletarCategoriaUseCase deletarCategoriaUseCase, CadastrarCategoriaUseCase cadastrarCategoriaUseCase, ListarCategoriasUseCase listarCategoriasUseCase, BuscarCategoriaUseCase buscarCategoriaUseCase, AtualizarCategoriaUseCase atualizarCategoriaUseCase) {
        this.deletarCategoriaUseCase = deletarCategoriaUseCase;
        this.cadastrarCategoriaUseCase = cadastrarCategoriaUseCase;
        this.listarCategoriasUseCase = listarCategoriasUseCase;
        this.buscarCategoriaUseCase = buscarCategoriaUseCase;
        this.atualizarCategoriaUseCase = atualizarCategoriaUseCase;
    }

    @GetMapping("/listarTodasCategorias")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    @Operation(summary = "lista categoria cadastrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem  bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasCategorias() {
        return ResponseEntity.ok(listarCategoriasUseCase.listarTodas());
    }

    @PostMapping("/cadastrarCategoria")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Cadastrar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro  bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoria = cadastrarCategoriaUseCase.executar(categoriaRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(location).body(categoria);
    }

    @GetMapping("/buscarCategoriaPorId/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    @Operation(summary = "Busca categoria por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por id bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buscarCategoriaUseCase.buscarPorId(id));
    }

    @DeleteMapping("/deletarCategoria/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Deletar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleção bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        deletarCategoriaUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizarCategoria/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Atualizar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid AtualizarCategoriaRequestDTO atualizarCategoriaRequestDTO) {

        CategoriaResponseDTO categoriaAtualizada = atualizarCategoriaUseCase.executar(id, atualizarCategoriaRequestDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }
}