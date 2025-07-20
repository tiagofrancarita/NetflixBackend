package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.application.usecase.*;
import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.interfaces.dto.TemporadaRequestDTO;
import br.com.franca.netflix.interfaces.dto.TemporadaResponseDTO;
import br.com.franca.netflix.interfaces.mapper.TemporadaMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/temporadas")
public class TemporadaController {

    private final CadastrarTemporadaUseCase cadastrarTemporadaUseCase;
    private final AtualizarTemporadaUseCase atualizarTemporadaUseCase;
    private final BuscarTemporadaPorIdUseCase buscarTemporadaPorIdUseCase;
    private final ListarTemporadasUseCase listarTemporadasUseCase;
    private final DeletarTemporadaUseCase deletarTemporadaUseCase;
    private final TemporadaMapper temporadaMapper;

    public TemporadaController(CadastrarTemporadaUseCase cadastrarTemporadaUseCase, AtualizarTemporadaUseCase atualizarTemporadaUseCase, BuscarTemporadaPorIdUseCase buscarTemporadaPorIdUseCase,
                               ListarTemporadasUseCase listarTemporadasUseCase, DeletarTemporadaUseCase deletarTemporadaUseCase, TemporadaMapper temporadaMapper) {
        this.cadastrarTemporadaUseCase = cadastrarTemporadaUseCase;
        this.atualizarTemporadaUseCase = atualizarTemporadaUseCase;
        this.buscarTemporadaPorIdUseCase = buscarTemporadaPorIdUseCase;
        this.listarTemporadasUseCase = listarTemporadasUseCase;
        this.deletarTemporadaUseCase = deletarTemporadaUseCase;
        this.temporadaMapper = temporadaMapper;
    }

    @PostMapping("/cadastrarTemporada")
    public ResponseEntity<TemporadaResponseDTO> cadastrarTemporada(@Valid @RequestBody TemporadaRequestDTO dto) {
        log.info("🎬 Criando nova temporada: {}", dto);

        // Converter DTO para model
        var temporadaModel = temporadaMapper.toModel(dto);

        // Executar o use case com o model convertido
        var temporada = cadastrarTemporadaUseCase.executar(temporadaModel);

        // Converter resposta para DTO
        return ResponseEntity.ok(temporadaMapper.toResponse(temporada));
    }

    @GetMapping("/buscarTemporadaPorId/{id}")
    public ResponseEntity<TemporadaResponseDTO> buscarTemporadaPorId(@PathVariable Long id) {
        Temporada temporada = buscarTemporadaPorIdUseCase.executar(id);
        return ResponseEntity.ok(temporadaMapper.toResponse(temporada));
    }

    @GetMapping("/listarTodasTemporadas")
    public ResponseEntity<List<TemporadaResponseDTO>> listarTodasTemporadas() {
        var temporadas = listarTemporadasUseCase.executar();
        return ResponseEntity.ok(temporadaMapper.toResponseList(temporadas));
    }

    @DeleteMapping("/deletarTemporadaPorID/{id}")
    public ResponseEntity<Void> deletarTemporadaPorID(@PathVariable Long id) {
        deletarTemporadaUseCase.executar(id);
        log.info("🗑️ Temporada com ID {} removida com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizarTemporadaPorId/{id}")
    public ResponseEntity<TemporadaResponseDTO> atualizarTemporadaPorId(@PathVariable Long id, @Valid @RequestBody TemporadaRequestDTO dto) {
        log.info("🎬 Requisição para atualizar temporada ID {}: {}", id, dto);
        Temporada temporadaAtualizada = atualizarTemporadaUseCase.executar(id, dto);
        return ResponseEntity.ok(temporadaMapper.toResponse(temporadaAtualizada));
    }
}