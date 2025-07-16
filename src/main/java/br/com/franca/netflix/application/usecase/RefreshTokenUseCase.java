package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.RefreshToken;
import br.com.franca.netflix.domain.repository.RefreshTokenRepository;
import br.com.franca.netflix.interfaces.dto.RefreshTokenResponseDTO;
import br.com.franca.netflix.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefreshTokenUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenUseCase(JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshTokenResponseDTO executar(String refreshToken) {
        RefreshToken token = refreshTokenRepository.buscarPorToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (token.getDataExpiracao().before(new Date())) {
            throw new RuntimeException("Refresh token expirado");
        }

        String novoAccessToken = jwtTokenProvider.gerarToken(token.getEmail());

        return new RefreshTokenResponseDTO(novoAccessToken, refreshToken, "Bearer");
    }
}
