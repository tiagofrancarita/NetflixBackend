package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.CategoriaJaCadastradaException;
import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.domain.repository.CategoriaRepository;
import br.com.franca.netflix.interfaces.dto.CategoriaRequestDTO;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastrarCategoriaUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarCategoriaUseCase.class);
    private final CategoriaRepository categoriaRepository;

    public CadastrarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO executar(CategoriaRequestDTO categoriaRequest){

        logger.info("Iniciando cadastro de categoria: {}", categoriaRequest.getNome());

        validarEntrada(categoriaRequest);

        if (categoriaRepository.existePorNome(categoriaRequest.getNome())) {
            logger.warn("Categoria já cadastrada: {}", categoriaRequest.getNome());
            throw new CategoriaJaCadastradaException(categoriaRequest.getNome());
        }

        Categoria categoria = new Categoria(null, categoriaRequest.getNome());
        Categoria salva = categoriaRepository.salvar(categoria);

        logger.info("Categoria cadastrada com sucesso: {} (ID: {})", salva.getNome(), salva.getId());

        return CategoriaResponseDTO.builder()
                .id(salva.getId())
                .nome(salva.getNome())
                .build();
    }

    private void validarEntrada(CategoriaRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório.");
        }
    }
}