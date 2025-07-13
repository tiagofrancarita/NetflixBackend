package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.EmailJaCadastradoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarUsuarioUseCase.class);


    private final UsuarioRepository usuarioRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioJpaRepository usuarioJpaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario executar(Usuario request) {

        logger.info("Iniciando cadastro para o e-mail: {}", request.getEmail());

        try {
            String senhaCriptografada = passwordEncoder.encode(request.getSenha());

            Usuario usuario = Usuario.builder()
                    .nome(request.getNome())
                    .email(request.getEmail())
                    .senha(senhaCriptografada)
                    .cpf(request.getCpf())
                    .dataNascimento(request.getDataNascimento())
                    .build();

            Usuario salvo = usuarioRepository.salvar(usuario);

            logger.info("Cadastro realizado com sucesso para o e-mail: {} (ID: {})", salvo.getEmail(), salvo.getId());

            return salvo;
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("usuarios.email")) {
                logger.warn("Tentativa de cadastro com e-mail duplicado: {}", request.getEmail());
                throw new EmailJaCadastradoException(request.getEmail());
            }
            logger.error("Violação de integridade no cadastro de e-mail {}: {}", request.getEmail(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao cadastrar o usuário com e-mail {}: {}", request.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}