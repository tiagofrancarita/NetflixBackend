package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarUsuariosUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ListarUsuariosUseCase.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponse> listarTodos() {
        logger.info("================ Iniciando a listagem de usuarios");
        var usuarios = usuarioRepository.listarTodos();
        logger.info("============ usuarios encontrados:  " +usuarios.size());
        System.out.println("Usuarios encontrados: " + usuarios.size());
        logger.info("================== Listagem concluida com sucesso.");
        return usuarioMapper.toResponseList(usuarioRepository.listarTodos());
    }
}