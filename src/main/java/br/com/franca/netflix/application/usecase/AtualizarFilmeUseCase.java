package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.FilmeNaoEncontradoException;
import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import br.com.franca.netflix.interfaces.dto.FilmeAtualizacaoDTO;
import br.com.franca.netflix.interfaces.dto.FilmeRequestDTO;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import br.com.franca.netflix.interfaces.mapper.FilmeMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarFilmeUseCase {


    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public AtualizarFilmeUseCase(FilmeRepository filmeRepository, FilmeMapper filmeMapper) {
        this.filmeRepository = filmeRepository;
        this.filmeMapper = filmeMapper;
    }

    @Transactional
    public FilmeResponseDTO executar(Long id, FilmeAtualizacaoDTO filmeAtualizacaoDTO) {
        Filme filme = filmeRepository.buscarPorId(id)
                .orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado com id: " + id));

        if (filmeAtualizacaoDTO.getTitulo() != null) filme.setTitulo(filmeAtualizacaoDTO.getTitulo());
        if (filmeAtualizacaoDTO.getDescricao() != null) filme.setDescricao(filmeAtualizacaoDTO.getDescricao());
        if (filmeAtualizacaoDTO.getMidia() != null) filme.setMidia(filmeAtualizacaoDTO.getMidia());
        if (filmeAtualizacaoDTO.getImagemUrl() != null) filme.setImagemUrl(filmeAtualizacaoDTO.getImagemUrl());
        if (filmeAtualizacaoDTO.getVideoUrl() != null) filme.setVideoUrl(filmeAtualizacaoDTO.getVideoUrl());
        if (filmeAtualizacaoDTO.getAno() != null) filme.setAno(filmeAtualizacaoDTO.getAno());
        if (filmeAtualizacaoDTO.getDuracao() != null) filme.setDuracao(filmeAtualizacaoDTO.getDuracao());
        if (filmeAtualizacaoDTO.getCategoriaId() != null) filme.setCategoriaId(filmeAtualizacaoDTO.getCategoriaId());

        Filme filmeAtualizado = filmeRepository.salvar(filme);
        return filmeMapper.toResponse(filmeAtualizado);
    }
}