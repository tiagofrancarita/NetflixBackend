package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoNaoEncontradoException;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarTemporadaUseCase {

    private final TemporadaRepository temporadaRepository;

    public void executar(Long id) {
        if (!temporadaRepository.existePorId(id)) {
            log.warn("❌ Temporada com ID {} não encontrada para exclusão", id);
            throw new RecursoNaoEncontradoException("Temporada não encontrada");
        }

        temporadaRepository.deletar(id);
        log.info("🗑️ Temporada com ID {} removida com sucesso", id);
    }
}
