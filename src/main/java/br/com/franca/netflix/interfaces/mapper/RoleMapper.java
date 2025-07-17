package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Role;
import br.com.franca.netflix.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getNome());
    }

    public RoleEntity toEntity(Role domain) {
        return RoleEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .build();
    }
}