package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.FilmeNaoEncontradoException;
import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import br.com.franca.netflix.interfaces.mapper.FilmeMapper;
import org.springframework.stereotype.Service;

@Service
public class BuscarFilmeUseCase {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public BuscarFilmeUseCase(FilmeRepository filmeRepository, FilmeMapper filmeMapper) {
        this.filmeRepository = filmeRepository;
        this.filmeMapper = filmeMapper;
    }

    public FilmeResponseDTO buscarPorId(Long id) {
        Filme filme = filmeRepository.buscarPorId(id)
                .orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado com id: " + id));
        return filmeMapper.toResponse(filme);
    }

}