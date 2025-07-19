package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.infrastructure.persistence.entity.FilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeJpaRepository extends JpaRepository<FilmeEntity, Long> {

}
