package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.Role;
import br.com.franca.netflix.domain.repository.RoleRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.RoleJpaRepository;
import br.com.franca.netflix.interfaces.mapper.RoleMapper;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository, RoleMapper roleMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Set<Role> buscarPorUsuarioId(Long usuarioId) {
        return roleJpaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(roleMapper::toDomain)
                .collect(Collectors.toSet());
    }
}