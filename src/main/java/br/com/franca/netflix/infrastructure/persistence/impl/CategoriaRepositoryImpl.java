package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.domain.repository.CategoriaRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.CategoriaEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.CategoriaJpaRepository;
import br.com.franca.netflix.interfaces.mapper.CategoriaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final CategoriaJpaRepository categoriaJpaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaRepositoryImpl(CategoriaJpaRepository categoriaJpaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaJpaRepository = categoriaJpaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public Categoria salvar(Categoria categoria) {
        CategoriaEntity categoriaEntity = categoriaMapper.toEntity(categoria);
        CategoriaEntity categoriaSalva = categoriaJpaRepository.save(categoriaEntity);
        return categoriaMapper.toDomain(categoriaSalva);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaJpaRepository.findById(id).map(categoriaMapper::toDomain);
    }

    @Override
    public void deletarPorId(Long id) {
        categoriaJpaRepository.deleteById(id);
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaJpaRepository.findAll().stream().map(categoriaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Categoria> buscarPorNome(String nome) {
        return categoriaJpaRepository.findByNome(nome)
                .map(categoriaMapper::toDomain);
    }

    @Override
    public boolean existePorNome(String nome) {
        return categoriaJpaRepository.existsByNome(nome);
    }
}