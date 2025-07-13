package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.config.JwtProperties;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.JwtResponseDTO;
import br.com.franca.netflix.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponseDTO autenticarUsuario(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtTokenProvider.gerarToken(email);
        Date agora = new Date();

        return new JwtResponseDTO(
                jwtProperties.getTokenType(),
                jwtProperties.getExpiration(),
                agora,
                token,
                jwtProperties.getEncryptKey(),
                jwtProperties.getSignKey()
        );
    }
}
