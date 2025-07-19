package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.repository.CategoriaRepository;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
import br.com.franca.netflix.interfaces.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private static final Logger logger = LoggerFactory.getLogger(ListarCategoriasUseCase.class);

    public ListarCategoriasUseCase(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public List<CategoriaResponseDTO> listarTodas() {
        logger.info("Iniciando listagem de categorias...");

        var categorias = categoriaRepository.listarTodas();

        logger.info("Total de categorias encontradas: {}", categorias.size());

        return categoriaMapper.toCategoriaResponseList(categorias);
    }
}