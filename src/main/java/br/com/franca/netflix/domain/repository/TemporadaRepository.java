package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Temporada;
import java.util.List;
import java.util.Optional;

public interface TemporadaRepository {

    Temporada salvar(Temporada temporada);
    Optional<Temporada> buscarPorId(Long id);
    List<Temporada> listarTodas();
    void deletar(Long id);
    boolean existePorId(Long id);
}
