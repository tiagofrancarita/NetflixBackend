package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import br.com.franca.netflix.interfaces.dto.FilmeRequestDTO;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import br.com.franca.netflix.interfaces.mapper.FilmeMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CadastrarFilmeUseCase {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public CadastrarFilmeUseCase(FilmeRepository filmeRepository, FilmeMapper filmeMapper) {
        this.filmeRepository = filmeRepository;
        this.filmeMapper = filmeMapper;
    }

    @Transactional
    public FilmeResponseDTO executar(FilmeRequestDTO requestDTO) {
        Filme filme = filmeMapper.toDomain(requestDTO);
        Filme salvo = filmeRepository.salvar(filme);
        return filmeMapper.toResponse(salvo);
    }
}