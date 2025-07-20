package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Episodio;
import java.util.List;
import java.util.Optional;

public interface EpisodioRepository {

    Episodio salvar(Episodio episodio);
    Optional<Episodio> buscarPorId(Long id);
    List<Episodio> listarTodos();
    void deletar(Long id);
    boolean existePorId(Long id);

}