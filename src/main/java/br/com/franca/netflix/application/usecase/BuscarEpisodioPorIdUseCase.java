package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoNaoEncontradoException;
import br.com.franca.netflix.domain.model.Episodio;
import br.com.franca.netflix.domain.repository.EpisodioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarEpisodioPorIdUseCase {

    private final EpisodioRepository episodioRepository;

    public Episodio executar(Long id) {
        return episodioRepository.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("❌ Episódio com ID {} não encontrado", id);
                    return new RecursoNaoEncontradoException("Episódio não encontrado");
                });
    }
}
