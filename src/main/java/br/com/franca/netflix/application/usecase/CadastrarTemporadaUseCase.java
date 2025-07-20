package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CadastrarTemporadaUseCase {

    private final TemporadaRepository temporadaRepository;

    public Temporada executar(Temporada temporada) {
        if (temporada.getSerieId() == null) {
            log.error("❌ Série ID obrigatório ao cadastrar temporada");
            throw new IllegalArgumentException("ID da série é obrigatório");
        }

        Temporada salva = temporadaRepository.salvar(temporada);
        log.info("✅ Temporada cadastrada com ID {}", salva.getId());
        return salva;
    }
}