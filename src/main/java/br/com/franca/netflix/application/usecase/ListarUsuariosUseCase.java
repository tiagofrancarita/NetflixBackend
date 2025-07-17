package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponse> listarTodos() {
        var usuarios = usuarioRepository.listarTodos();
        System.out.println("Usuarios encontrados: " + usuarios.size());
        return usuarioMapper.toResponseList(usuarioRepository.listarTodos());
    }
}