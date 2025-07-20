package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoNaoEncontradoException;
import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import br.com.franca.netflix.interfaces.dto.TemporadaRequestDTO;
import br.com.franca.netflix.interfaces.mapper.TemporadaMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtualizarTemporadaUseCase {

    private static final Logger log = LoggerFactory.getLogger(AtualizarTemporadaUseCase.class);

    private final TemporadaRepository temporadaRepository;
    private final TemporadaMapper temporadaMapper;

    public AtualizarTemporadaUseCase(TemporadaRepository temporadaRepository, TemporadaMapper temporadaMapper) {
        this.temporadaRepository = temporadaRepository;
        this.temporadaMapper = temporadaMapper;
    }

    @Transactional
    public Temporada executar(Long id, TemporadaRequestDTO dto) {
        log.info("🎬 Atualizando temporada com ID {}: {}", id, dto);

        Temporada temporadaExistente = temporadaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Temporada não encontrada com ID " + id));

        // Validação básica (exemplo)
        if (dto.getNumero() == null) {
            throw new IllegalArgumentException("Número da temporada é obrigatório");
        }
        if (dto.getSerieId() == null) {
            throw new IllegalArgumentException("ID da série é obrigatório");
        }

        // Atualiza os campos (não atualize id)
        temporadaExistente.setNumero(dto.getNumero());
        temporadaExistente.setTitulo(dto.getTitulo());
        temporadaExistente.setDescricao(dto.getDescricao());
        temporadaExistente.setSerieId(dto.getSerieId());

        Temporada atualizado = temporadaRepository.salvar(temporadaExistente);
        log.info("🎬 Temporada atualizada com sucesso: {}", atualizado);
        return atualizado;
    }
}