package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.infrastructure.persistence.entity.TemporadaEntity;
import br.com.franca.netflix.interfaces.dto.TemporadaResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TemporadaMapper {

    public Temporada toDomain(TemporadaEntity temporadaEntity) {
        return Temporada.builder()
                .id(temporadaEntity.getId())
                .titulo(temporadaEntity.getTitulo())
                .numero(temporadaEntity.getNumero())
                .serieId(temporadaEntity.getSerieId())
                .build();
    }

    public TemporadaEntity toEntity(Temporada temporadaDomain) {
        return TemporadaEntity.builder()
                .id(temporadaDomain.getId())
                .titulo(temporadaDomain.getTitulo())
                .numero(temporadaDomain.getNumero())
                .serieId(temporadaDomain.getSerieId())
                .build();
    }

    public TemporadaResponseDTO toResponse(Temporada temporadaDomain) {
        return TemporadaResponseDTO.builder()
                .id(temporadaDomain.getId())
                .titulo(temporadaDomain.getTitulo())
                .numero(temporadaDomain.getNumero())
                .serieId(temporadaDomain.getSerieId())
                .build();
    }

    public List<TemporadaResponseDTO> toResponseList(List<Temporada> lista) {
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<Temporada> toDomainList(List<TemporadaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}