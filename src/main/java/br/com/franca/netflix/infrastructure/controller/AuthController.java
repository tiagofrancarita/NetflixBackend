package br.com.franca.netflix.infrastructure.controller;


import br.com.franca.netflix.application.usecase.AuthService;
import br.com.franca.netflix.application.usecase.RefreshTokenUseCase;
import br.com.franca.netflix.infrastructure.persistence.entity.RefreshTokenEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.RefreshTokenJpaRepository;
import br.com.franca.netflix.interfaces.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public AuthController(AuthService authService, RefreshTokenUseCase refreshTokenUseCase, RefreshTokenJpaRepository refreshTokenJpaRepository) {
        this.authService = authService;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequestDTO tokenDTO) {
        Optional<RefreshTokenEntity> tokenOpt = refreshTokenJpaRepository.findByToken(tokenDTO.getRefreshToken());
        if (tokenOpt.isEmpty()) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "Refresh token inválido ou não encontrado."));
        }

        refreshTokenJpaRepository.deletarPorToken(tokenDTO.getRefreshToken());
        return ResponseEntity.ok(new MessageResponseDTO("Logout realizado com sucesso."));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest) {

        JwtResponseDTO tokenDTO = authService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getSenha());
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO dto) {
        return ResponseEntity.ok(refreshTokenUseCase.executar(dto.getRefreshToken()));
    }
}
