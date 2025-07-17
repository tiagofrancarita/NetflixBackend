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
    private final UsuarioMapper mapper;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository, UsuarioMapper mapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity salvo = usuarioJpaRepository.save(entity);
        return mapper.toDomain(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioJpaRepository.findByNomeContainingIgnoreCase(nome).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioJpaRepository.findByCpf(cpf).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioJpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}