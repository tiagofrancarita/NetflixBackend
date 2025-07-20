package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Episodio;
import br.com.franca.netflix.domain.repository.EpisodioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarEpisodioUseCase {

    private final EpisodioRepository episodioRepository;

    public Episodio executar(Episodio episodio) {
        if (episodio.getTemporadaId() == null) {
            log.error("❌ Temporada ID obrigatório ao cadastrar episódio");
            throw new IllegalArgumentException("ID da temporada é obrigatório");
        }

        Episodio salvo = episodioRepository.salvar(episodio);
        log.info("✅ Episódio cadastrado com ID {}", salvo.getId());
        return salvo;
    }
}
