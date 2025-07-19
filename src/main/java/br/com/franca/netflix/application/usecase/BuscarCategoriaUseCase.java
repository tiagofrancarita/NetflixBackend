package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.CategoriaNãoEncontradaException;
import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.domain.repository.CategoriaRepository;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
import br.com.franca.netflix.interfaces.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarCategoriaUseCase {

    private static final Logger logger = LoggerFactory.getLogger(BuscarCategoriaUseCase.class);

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public BuscarCategoriaUseCase(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        logger.info("Buscando categoria pelo id: {}", id);

        Categoria categoria = categoriaRepository.buscarPorId(id)
                .orElseThrow(() -> new CategoriaNãoEncontradaException(
                        "Categoria com o id: " + id + " não encontrada na base de dados."));

        return categoriaMapper.toCategoriaResponse(categoria);
    }

    public CategoriaResponseDTO buscarPorNome(String nome) {
        logger.info("Buscando categoria pelo nome: {}", nome);

        Categoria categoria = categoriaRepository.buscarPorNome(nome)
                .orElseThrow(() -> new CategoriaNãoEncontradaException(
                        "Categoria com o nome: " + nome + " não encontrada na base de dados."));

        return categoriaMapper.toCategoriaResponse(categoria);
    }
}