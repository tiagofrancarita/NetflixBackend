package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Categoria;
import java.util.*;

public interface CategoriaRepository {

    Categoria salvar(Categoria categoria);
    void deletarPorId(Long id);
    Optional<Categoria> buscarPorId(Long id);
    List<Categoria> listarTodas();
    Optional<Categoria> buscarPorNome(String nome);
    boolean existePorNome(String nome);
}
