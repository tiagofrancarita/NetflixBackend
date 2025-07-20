package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Episodio;
import br.com.franca.netflix.domain.repository.EpisodioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListarEpisodiosUseCase {

    private final EpisodioRepository episodioRepository;

    public List<Episodio> executar() {
        List<Episodio> episodios = episodioRepository.listarTodos();
        log.info("🎞️ Listando {} episódios", episodios.size());
        return episodios;
    }
}
