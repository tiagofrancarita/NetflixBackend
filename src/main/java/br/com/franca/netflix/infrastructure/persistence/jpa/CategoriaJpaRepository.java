package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.infrastructure.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNome(String nome);
    boolean existsByNome(String nome);
    Optional<CategoriaEntity> findById(Long id);


}
