package br.com.franca.netflix.infrastructure.controller;


import br.com.franca.netflix.application.usecase.AuthService;
import br.com.franca.netflix.interfaces.dto.JwtResponseDTO;
import br.com.franca.netflix.interfaces.dto.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
        JwtResponseDTO tokenDTO = authService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getSenha());
        return ResponseEntity.ok(tokenDTO);




        // Aqui você pode implementar autenticação real (ex: via AuthenticationManager)
        //JwtResponseDTO tokenDTO = authService.autenticarUsuario(username);
        //return ResponseEntity.ok(tokenDTO);
    }
}
