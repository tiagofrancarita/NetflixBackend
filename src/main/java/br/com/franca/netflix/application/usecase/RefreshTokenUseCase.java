package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.RefreshToken;
import br.com.franca.netflix.domain.repository.RefreshTokenRepository;
import br.com.franca.netflix.interfaces.dto.RefreshTokenResponseDTO;
import br.com.franca.netflix.security.CustomUserDetailsService;
import br.com.franca.netflix.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenUseCase {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenUseCase(CustomUserDetailsService customUserDetailsService, JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshTokenResponseDTO executar(String refreshTokenStr) {
        RefreshToken token = refreshTokenRepository.buscarPorToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (token.getDataExpiracao().before(new Date())) {
            refreshTokenRepository.deletarPorEmail(token.getEmail()); // segurança extra
            throw new RuntimeException("Refresh token expirado");
        }

        // Gerar novo access token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(token.getEmail());
        String novoAccessToken = jwtTokenProvider.gerarToken(userDetails);

        // Gerar novo refresh token com validade de 7 dias
        String novoRefreshTokenStr = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date novaDataExpiracao = calendar.getTime();

        // Remover token antigo
        refreshTokenRepository.deletarPorEmail(token.getEmail());

        // Criar e salvar novo token
        RefreshToken novoRefreshToken = RefreshToken.builder()
                .token(novoRefreshTokenStr)
                .email(token.getEmail())
                .dataExpiracao(novaDataExpiracao)
                .build();

        refreshTokenRepository.salvar(novoRefreshToken);

        return new RefreshTokenResponseDTO(novoAccessToken, novoRefreshTokenStr, "Bearer");
    }
}
