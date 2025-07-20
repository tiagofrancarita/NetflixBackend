package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Episodio;
import br.com.franca.netflix.domain.repository.EpisodioRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.EpisodioJpaRepository;
import br.com.franca.netflix.interfaces.mapper.EpisodioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EpisodioRepositoryImpl implements EpisodioRepository {

    private final EpisodioJpaRepository episodioJpaRepository;
    private final EpisodioMapper episodioMapper;

    public EpisodioRepositoryImpl(EpisodioJpaRepository episodioJpaRepository, EpisodioMapper episodioMapper) {
        this.episodioJpaRepository = episodioJpaRepository;
        this.episodioMapper = episodioMapper;
    }

    @Override
    public Episodio salvar(Episodio episodio) {
        return episodioMapper.toDomain(episodioJpaRepository.save(episodioMapper.toEntity(episodio)));
    }

    @Override
    public Optional<Episodio> buscarPorId(Long id) {
        return episodioJpaRepository.findById(id).map(episodioMapper::toDomain);
    }

    @Override
    public List<Episodio> listarTodos() {
        return episodioMapper.toDomainList(episodioJpaRepository.findAll());
    }

    @Override
    public void deletar(Long id) {
        episodioJpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return episodioJpaRepository.existsById(id);
    }
}