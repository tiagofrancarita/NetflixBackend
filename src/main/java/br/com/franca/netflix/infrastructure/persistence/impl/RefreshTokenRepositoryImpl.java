package br.com.franca.netflix.infrastructure.persistence.impl;

import br.com.franca.netflix.domain.model.RefreshToken;
import br.com.franca.netflix.domain.repository.RefreshTokenRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.RefreshTokenEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.RefreshTokenJpaRepository;
import br.com.franca.netflix.interfaces.mapper.RefreshTokenMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenRepositoryImpl(RefreshTokenJpaRepository refreshTokenJpaRepository, RefreshTokenMapper refreshTokenMapper) {
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
        this.refreshTokenMapper = refreshTokenMapper;
    }

    @Override
    public RefreshToken salvar(RefreshToken refreshToken) {
        RefreshTokenEntity entity = refreshTokenMapper.toEntity(refreshToken);
        return refreshTokenMapper.toDomain(refreshTokenJpaRepository.save(entity));
    }

    @Override
    public Optional<RefreshToken> buscarPorToken(String token) {
        return refreshTokenJpaRepository.findByToken(token).map(refreshTokenMapper::toDomain);

    }

    @Override
    public void deletarPorEmail(String email) {
        refreshTokenJpaRepository.deleteByEmail(email);

    }
}