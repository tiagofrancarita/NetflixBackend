package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Role;
import java.util.Set;

public interface RoleRepository {

    Set<Role> buscarPorUsuarioId(Long usuarioId);

}