package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Filme;
import java.util.*;


public interface FilmeRepository {

    Filme salvar(Filme filme);
    Optional<Filme> buscarPorId(Long id);
    List<Filme> listarTodos();
    void deletarPorId(Long id);
    boolean existePorId(Long id);

}