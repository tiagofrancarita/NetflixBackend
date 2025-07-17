package br.com.franca.netflix.infrastructure.persistence.jpa;

import br.com.franca.netflix.infrastructure.persistence.entity.RefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.token = :token")
    void deletarPorToken(@Param("token") String token);

}
