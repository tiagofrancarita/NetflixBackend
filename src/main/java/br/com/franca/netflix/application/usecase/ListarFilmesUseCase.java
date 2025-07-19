package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import br.com.franca.netflix.interfaces.mapper.FilmeMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListarFilmesUseCase {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public ListarFilmesUseCase(FilmeRepository filmeRepository, FilmeMapper filmeMapper) {
        this.filmeRepository = filmeRepository;
        this.filmeMapper = filmeMapper;
    }

    public List<FilmeResponseDTO> listarTodos() {
        List<Filme> filmes = filmeRepository.listarTodos();
        return filmes.stream()
                .map(filmeMapper::toResponse)
                .collect(Collectors.toList());
    }
}