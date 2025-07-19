package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.FilmeNaoEncontradoException;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeletarFilmeUseCase {

    private static final Logger logger = LoggerFactory.getLogger(DeletarFilmeUseCase.class);

    private final FilmeRepository filmeRepository;

    public DeletarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional
    public void executar(Long id) {
        if (!filmeRepository.existePorId(id)) {
            throw new FilmeNaoEncontradoException("Filme não encontrado com id: " + id);
        }

        filmeRepository.deletarPorId(id);
        logger.info("🎬 Filme com ID {} removido com sucesso", id);
    }
}