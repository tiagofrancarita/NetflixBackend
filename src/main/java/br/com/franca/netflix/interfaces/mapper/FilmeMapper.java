package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.infrastructure.persistence.entity.FilmeEntity;
import br.com.franca.netflix.interfaces.dto.FilmeRequestDTO;
import br.com.franca.netflix.interfaces.dto.FilmeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class FilmeMapper {

    public Filme toDomain(FilmeEntity filmeEntity) {
        return Filme.builder()
                .id(filmeEntity.getId())
                .titulo(filmeEntity.getTitulo())
                .descricao(filmeEntity.getDescricao())
                .midia(filmeEntity.getMidia())
                .imagemUrl(filmeEntity.getImagemUrl())
                .videoUrl(filmeEntity.getVideoUrl())
                .ano(filmeEntity.getAno())
                .duracao(filmeEntity.getDuracao())
                .categoriaId(filmeEntity.getCategoriaId())
                .build();
    }

    public FilmeEntity toEntity(Filme filmeDomain) {
        return FilmeEntity.builder()
                .id(filmeDomain.getId())
                .titulo(filmeDomain.getTitulo())
                .descricao(filmeDomain.getDescricao())
                .midia(filmeDomain.getMidia())
                .imagemUrl(filmeDomain.getImagemUrl())
                .videoUrl(filmeDomain.getVideoUrl())
                .ano(filmeDomain.getAno())
                .duracao(filmeDomain.getDuracao())
                .categoriaId(filmeDomain.getCategoriaId())
                .build();
    }

    public FilmeResponseDTO toResponse(Filme filme) {
        return FilmeResponseDTO.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .descricao(filme.getDescricao())
                .midia(filme.getMidia())
                .imagemUrl(filme.getImagemUrl())
                .videoUrl(filme.getVideoUrl())
                .ano(filme.getAno())
                .duracao(filme.getDuracao())
                .categoriaId(filme.getCategoriaId())
                .build();
    }

    public Filme toDomain(FilmeRequestDTO filmeRequestDTO) {
        return Filme.builder()
                .titulo(filmeRequestDTO.getTitulo())
                .descricao(filmeRequestDTO.getDescricao())
                .midia(filmeRequestDTO.getMidia())
                .imagemUrl(filmeRequestDTO.getImagemUrl())
                .videoUrl(filmeRequestDTO.getVideoUrl())
                .ano(filmeRequestDTO.getAno())
                .duracao(filmeRequestDTO.getDuracao())
                .categoriaId(filmeRequestDTO.getCategoriaId())
                .build();
    }
}