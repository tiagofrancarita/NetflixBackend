package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListarTemporadasUseCase {

    private final TemporadaRepository temporadaRepository;

    public List<Temporada> executar() {
        List<Temporada> temporadas = temporadaRepository.listarTodas();
        log.info("📺 Listando {} temporadas", temporadas.size());
        return temporadas;
    }

}
