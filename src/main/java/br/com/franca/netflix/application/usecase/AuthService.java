package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.config.JwtProperties;
import br.com.franca.netflix.domain.enums.StatusUsuario;
import br.com.franca.netflix.domain.model.RefreshToken;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.RefreshTokenRepository;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.JwtResponseDTO;
import br.com.franca.netflix.security.CustomUserDetailsService;
import br.com.franca.netflix.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(CustomUserDetailsService customUserDetailsService, JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
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

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        String accessToken = jwtTokenProvider.gerarToken(userDetails);

        // Antes de gerar novo token
        refreshTokenRepository.deletarPorEmail(email); // <- ADICIONE ISSO AQUI

        // Gerar refresh token único
        String refreshTokenStr = UUID.randomUUID().toString();

        // Definir expiração para refresh token (exemplo 7 dias)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        var refreshTokenExpiry = calendar.getTime();

        // Criar e salvar o RefreshToken
        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenStr)
                .email(email)
                .dataExpiracao(refreshTokenExpiry)
                .build();

        refreshTokenRepository.salvar(refreshToken);

        Date agora = new Date();

        logger.info("Login bem-sucedido para o e-mail: {}", email);

        // Retorna o DTO com accessToken e refreshToken
        return new JwtResponseDTO(
                jwtProperties.getTokenType(),
                jwtProperties.getExpiration(),
                agora,
                accessToken,
                refreshTokenStr,               // novo parâmetro: refreshToken
                jwtProperties.getEncryptKey(),
                jwtProperties.getSignKey()
        );
    }
}