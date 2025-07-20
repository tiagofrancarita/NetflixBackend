package br.com.franca.netflix.infrastructure.persistence.jpa;

import org.springframework.stereotype.Repository;
import br.com.franca.netflix.infrastructure.persistence.entity.EpisodioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface EpisodioJpaRepository extends JpaRepository<EpisodioEntity, Long> {

    List<EpisodioEntity> findByTemporadaId(Long temporadaId);
}
