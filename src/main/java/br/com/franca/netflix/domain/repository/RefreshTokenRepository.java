package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken salvar(RefreshToken refreshToken);
    Optional<RefreshToken> buscarPorToken(String token);
    void deletarPorEmail(String email);

}
