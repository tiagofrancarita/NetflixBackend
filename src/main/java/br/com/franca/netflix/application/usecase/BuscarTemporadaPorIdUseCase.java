package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoNaoEncontradoException;
import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarTemporadaPorIdUseCase {

    private final TemporadaRepository temporadaRepository;

    public Temporada executar(Long id) {
        return temporadaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Temporada não encontrada com ID " + id));
    }

}
