package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.RefreshToken;
import br.com.franca.netflix.infrastructure.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    public RefreshToken toDomain(RefreshTokenEntity refreshTokenEntity) {
        return new RefreshToken(
                refreshTokenEntity.getId(),
                refreshTokenEntity.getToken(),
                refreshTokenEntity.getEmail(),
                refreshTokenEntity.getDataExpiracao()
        );
    }

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
                .id(refreshToken.getId())
                .token(refreshToken.getToken())
                .email(refreshToken.getEmail())
                .dataExpiracao(refreshToken.getDataExpiracao())
                .build();
    }
}