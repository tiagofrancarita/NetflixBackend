package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface RoleJpaRepository  extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM UsuarioEntity u JOIN u.roles r WHERE u.id = :usuarioId")
    Set<RoleEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}