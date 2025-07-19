package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.CategoriaNãoEncontradaException;
import br.com.franca.netflix.domain.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeletarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    public DeletarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public void deletar(Long id) {
        boolean existe = categoriaRepository.buscarPorId(id).isPresent();

        if (!existe) {
            throw new CategoriaNãoEncontradaException("Categoria com o ID " + id + " não encontrada.");
        }

        categoriaRepository.deletarPorId(id);
    }
}