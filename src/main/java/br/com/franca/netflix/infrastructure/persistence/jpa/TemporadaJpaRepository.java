package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.infrastructure.persistence.entity.TemporadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporadaJpaRepository extends JpaRepository<TemporadaEntity, Long> {

    List<TemporadaEntity> findBySerieId(Long serieId);
}