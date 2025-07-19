package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Filme;
import br.com.franca.netflix.domain.repository.FilmeRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.FilmeEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.FilmeJpaRepository;
import br.com.franca.netflix.interfaces.mapper.FilmeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FilmeRepositoryImpl implements FilmeRepository {

    private final FilmeJpaRepository filmeJpaRepository;
    private final FilmeMapper filmeMapper;

    public FilmeRepositoryImpl(FilmeJpaRepository filmeJpaRepository, FilmeMapper filmeMapper) {
        this.filmeJpaRepository = filmeJpaRepository;
        this.filmeMapper = filmeMapper;
    }

    @Override
    public boolean existePorId(Long id) {
        return filmeJpaRepository.existsById(id);
    }

    @Override
    public Filme salvar(Filme filme) {
        FilmeEntity entity = filmeMapper.toEntity(filme);
        FilmeEntity salvo = filmeJpaRepository.save(entity);
        return filmeMapper.toDomain(salvo);
    }

    @Override
    public Optional<Filme> buscarPorId(Long id) {
        return filmeJpaRepository.findById(id).map(filmeMapper::toDomain);
    }

    @Override
    public List<Filme> listarTodos() {
        return filmeJpaRepository.findAll().stream()
                .map(filmeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        filmeJpaRepository.deleteById(id);
    }
}