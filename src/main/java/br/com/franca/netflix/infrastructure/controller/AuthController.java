package br.com.franca.netflix.infrastructure.controller;


import br.com.franca.netflix.application.usecase.AuthService;
import br.com.franca.netflix.application.usecase.RefreshTokenUseCase;
import br.com.franca.netflix.interfaces.dto.JwtResponseDTO;
import br.com.franca.netflix.interfaces.dto.LoginRequest;
import br.com.franca.netflix.interfaces.dto.RefreshTokenRequestDTO;
import br.com.franca.netflix.interfaces.dto.RefreshTokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(AuthService authService, RefreshTokenUseCase refreshTokenUseCase) {
        this.authService = authService;
        this.refreshTokenUseCase = refreshTokenUseCase;
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
