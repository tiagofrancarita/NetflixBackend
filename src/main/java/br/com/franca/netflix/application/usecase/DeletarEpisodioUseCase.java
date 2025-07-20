package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoNaoEncontradoException;
import br.com.franca.netflix.domain.repository.EpisodioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarEpisodioUseCase {

    private final EpisodioRepository episodioRepository;

    public void executar(Long id) {
        if (!episodioRepository.existePorId(id)) {
            log.warn("❌ Episódio com ID {} não encontrado para exclusão", id);
            throw new RecursoNaoEncontradoException("Episódio não encontrado");
        }

        episodioRepository.deletar(id);
        log.info("🗑️ Episódio com ID {} removido com sucesso", id);
    }
}
