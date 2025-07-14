package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.config.JwtProperties;
import br.com.franca.netflix.domain.enums.StatusUsuario;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.JwtResponseDTO;
import br.com.franca.netflix.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponseDTO autenticarUsuario(String email, String senha) {

        logger.info("Iniciando autenticação para o e-mail: {}", email);

        var usuarioOptional = usuarioRepository.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            logger.warn("Tentativa de login com e-mail não cadastrado: {}", email);
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        if (usuario.getAtivo() != StatusUsuario.A) {
            logger.warn("Tentativa de login com usuário inativo: {}", email);
            throw new RuntimeException("Usuário está inativo. Acesso negado.");
        }

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            logger.warn("Senha inválida para o e-mail: {}", email);
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtTokenProvider.gerarToken(email);
        Date agora = new Date();

        logger.info("Login bem-sucedido para o e-mail: {}", email);

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