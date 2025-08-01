package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import br.com.franca.netflix.domain.exception.UsuarioNaoEncontradoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InativarUsuarioUseCase {

    private static final Logger logger = LoggerFactory.getLogger(InativarUsuarioUseCase.class);
    private final UsuarioRepository usuarioRepository;

    public InativarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void inativarPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        if (usuario.getAtivo() == StatusUsuario.I) {
            logger.info("Usuário com ID {} já está inativo.", id);
            return;
        }

        usuario.setAtivo(StatusUsuario.I);
        usuarioRepository.salvar(usuario);
        logger.info("Usuário com ID {} foi inativado com sucesso.", id);
    }

    public void inativarPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com CPF: " + cpf));

        if (usuario.getAtivo() == StatusUsuario.I) {
            logger.info("Usuário com CPF {} já está inativo.", cpf);
            return;
        }

        usuario.setAtivo(StatusUsuario.I);
        usuarioRepository.salvar(usuario);
        logger.info("Usuário com CPF {} foi inativado com sucesso.", cpf);
    }

    public Usuario executar(String email) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com e-mail " + email + " não encontrado"));

        if (usuario.getAtivo() == StatusUsuario.I) {
            logger.info("Usuário com e-mail {} já está inativo.", email);
            return usuario;
        }

        usuario.setAtivo(StatusUsuario.I);
        Usuario atualizado = usuarioRepository.salvar(usuario);

        logger.info("Usuário com e-mail {} foi inativado com sucesso.", email);
        return atualizado;
    }
}