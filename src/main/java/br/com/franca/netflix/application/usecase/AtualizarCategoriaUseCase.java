package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.CategoriaJaCadastradaException;
import br.com.franca.netflix.domain.exception.CategoriaNãoEncontradaException;
import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.domain.repository.CategoriaRepository;
import br.com.franca.netflix.interfaces.dto.AtualizarCategoriaRequestDTO;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
import br.com.franca.netflix.interfaces.mapper.CategoriaMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AtualizarCategoriaUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AtualizarCategoriaUseCase.class);

    private final CategoriaMapper categoriaMapper;
    private final CategoriaRepository categoriaRepository;

    public AtualizarCategoriaUseCase(CategoriaMapper categoriaMapper, CategoriaRepository categoriaRepository) {
        this.categoriaMapper = categoriaMapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaResponseDTO executar(Long id, AtualizarCategoriaRequestDTO atualizarCategoriaRequestDTO) {

        logger.info("Iniciando atualização de categoria ID: {}", id);

        Categoria categoria = categoriaRepository.buscarPorId(id)
                .orElseThrow(() -> {
                    logger.warn("Categoria com o id {} não encontrado para ser atualizada", id);
                    return new CategoriaNãoEncontradaException("Categoria com o ID: " + id + " Não encontrada");
                });

        logger.debug("Categoria atual antes da atualização: {}", categoria);

        if (atualizarCategoriaRequestDTO.getNome() != null && !atualizarCategoriaRequestDTO.getNome().isBlank()
                && !atualizarCategoriaRequestDTO.getNome().equalsIgnoreCase(categoria.getNome())) {

            logger.info("Solicitada atualização do nome de '{}' para '{}'", categoria.getNome(), atualizarCategoriaRequestDTO.getNome());

            categoriaRepository.buscarPorNome(atualizarCategoriaRequestDTO.getNome())
                    .ifPresent(u -> {
                        logger.warn("Tentativa de atualização para uma categoria já cadastrada: {}", atualizarCategoriaRequestDTO.getNome());
                        throw new CategoriaJaCadastradaException("Categoria já está em uso: " + atualizarCategoriaRequestDTO.getNome());
                    });

            categoria.setNome(atualizarCategoriaRequestDTO.getNome());
            logger.info("Categoria atualizada para: {}", categoria.getNome());
        }

        try {
            Categoria categoriaAtualizada = categoriaRepository.salvar(categoria);
            logger.info("Categoria atualizada com sucesso: ID: {}, nome {}", categoriaAtualizada.getId(), categoriaAtualizada.getNome());
            return categoriaMapper.toCategoriaResponse(categoriaAtualizada);

        } catch (DataIntegrityViolationException e) {
            logger.error("Erro de integridade ao atualizar categoria ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar categoria ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}