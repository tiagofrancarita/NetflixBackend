package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.UsuarioNaoEncontradoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public BuscarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado."));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com e-mail " + email + " não encontrado."));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse buscarPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com CPF " + cpf + " não encontrado."));
        return usuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponse> buscarPorNome(String nome) {
        return usuarioMapper.toResponseList(usuarioRepository.buscarPorNome(nome));
    }

}
