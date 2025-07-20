package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Episodio;
import br.com.franca.netflix.infrastructure.persistence.entity.EpisodioEntity;
import br.com.franca.netflix.interfaces.dto.EpisodioResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EpisodioMapper {

    public Episodio toDomain(EpisodioEntity episodioEntity) {
        return Episodio.builder()
                .id(episodioEntity.getId())
                .titulo(episodioEntity.getTitulo())
                .numero(episodioEntity.getNumero())
                .temporadaId(episodioEntity.getTemporadaId())
                .build();
    }

    public EpisodioEntity toEntity(Episodio episodioDomain) {
        return EpisodioEntity.builder()
                .id(episodioDomain.getId())
                .titulo(episodioDomain.getTitulo())
                .numero(episodioDomain.getNumero())
                .temporadaId(episodioDomain.getTemporadaId())
                .build();
    }

    public EpisodioResponseDTO toResponse(Episodio episodeDomainResponseDTO) {
        return EpisodioResponseDTO.builder()
                .id(episodeDomainResponseDTO.getId())
                .titulo(episodeDomainResponseDTO.getTitulo())
                .numero(episodeDomainResponseDTO.getNumero())
                .temporadaId(episodeDomainResponseDTO.getTemporadaId())
                .build();
    }

    public List<EpisodioResponseDTO> toResponseList(List<Episodio> lista) {
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<Episodio> toDomainList(List<EpisodioEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}