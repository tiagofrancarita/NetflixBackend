package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByCpf(String cpf);
    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

}
