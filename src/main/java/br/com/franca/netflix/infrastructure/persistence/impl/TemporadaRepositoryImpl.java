package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Temporada;
import br.com.franca.netflix.domain.repository.TemporadaRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.TemporadaJpaRepository;
import br.com.franca.netflix.interfaces.mapper.TemporadaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TemporadaRepositoryImpl implements TemporadaRepository {

    private final TemporadaJpaRepository temporadaJpaRepository;
    private final TemporadaMapper temporadaMapper;

    public TemporadaRepositoryImpl(TemporadaJpaRepository temporadaJpaRepository, TemporadaMapper temporadaMapper) {
        this.temporadaJpaRepository = temporadaJpaRepository;
        this.temporadaMapper = temporadaMapper;
    }

    @Override
    public Temporada salvar(Temporada temporada) {
        return temporadaMapper.toDomain(temporadaJpaRepository.save(temporadaMapper.toEntity(temporada)));
    }

    @Override
    public Optional<Temporada> buscarPorId(Long id) {
        return temporadaJpaRepository.findById(id).map(temporadaMapper::toDomain);
    }

    @Override
    public List<Temporada> listarTodas() {
        return temporadaMapper.toDomainList(temporadaJpaRepository.findAll());
    }

    @Override
    public void deletar(Long id) {
        temporadaJpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return temporadaJpaRepository.existsById(id);
    }
}