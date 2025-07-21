package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository, UsuarioMapper usuarioMapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        UsuarioEntity salvo = usuarioJpaRepository.save(entity);
        return usuarioMapper.toDomain(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email).map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioJpaRepository.findByNomeContainingIgnoreCase(nome).stream().map(usuarioMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioJpaRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioJpaRepository.findByCpf(cpf).map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioJpaRepository.findAll().stream().map(usuarioMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarAtivos() {
        return usuarioJpaRepository.findAllAtivos()
                .stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }
}