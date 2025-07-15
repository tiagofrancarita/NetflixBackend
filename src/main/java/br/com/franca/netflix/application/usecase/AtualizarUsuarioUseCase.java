package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.EmailJaCadastradoException;
import br.com.franca.netflix.domain.exception.UsuarioNaoEncontradoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.franca.netflix.interfaces.dto.AtualizarUsuarioRequest;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUsuarioUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AtualizarUsuarioUseCase.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
                                   PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse executar(Long id, AtualizarUsuarioRequest request) {

        logger.info("Iniciando atualização do usuário ID: {}", id);

        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> {
                    logger.warn("Usuário com ID {} não encontrado para atualização.", id);
                    return new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado.");
                });

        logger.debug("Usuário atual antes da atualização: {}", usuario);

        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())) {
            logger.info("Solicitada atualização do email de '{}' para '{}'", usuario.getEmail(), request.getEmail());
            usuarioRepository.buscarPorEmail(request.getEmail()).ifPresent(u -> {
                logger.warn("Tentativa de atualizar para email já cadastrado: {}", request.getEmail());
                throw new EmailJaCadastradoException("E-mail já está em uso: " + request.getEmail());
            });
            usuario.setEmail(request.getEmail());
            logger.debug("Email atualizado para: {}", usuario.getEmail());
        }

        if (request.getNome() != null) {
            logger.info("Atualizando nome para: {}", request.getNome());
            usuario.setNome(request.getNome());
        }

        if (request.getCpf() != null) {
            logger.info("Atualizando CPF para: {}", request.getCpf());
            usuario.setCpf(request.getCpf());
        }

        if (request.getDataNascimento() != null) {
            logger.info("Atualizando data de nascimento para: {}", request.getDataNascimento());
            usuario.setDataNascimento(request.getDataNascimento());
        }

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            logger.info("Atualizando senha para o usuário ID: {}", id);
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        if (request.getStatus() != null) {
            logger.info("Atualizando status para: {}", request.getStatus());
            usuario.setAtivo(request.getStatus());
        }

        try {
            Usuario atualizado = usuarioRepository.salvar(usuario);
            logger.info("Usuário atualizado com sucesso: ID {}, Email {}", atualizado.getId(), atualizado.getEmail());
            return usuarioMapper.toResponse(atualizado);
        } catch (DataIntegrityViolationException e) {
            logger.error("Erro de integridade ao atualizar usuário ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar usuário ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
