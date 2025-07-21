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
        logger.info("================ Iniciando a listagem de usuários ATIVOS");

        var usuariosAtivos = usuarioRepository.listarAtivos();

        logger.info("============ usuários ativos encontrados: " + usuariosAtivos.size());
        System.out.println("Usuários ativos encontrados: " + usuariosAtivos.size());

        logger.info("================== Listagem concluída com sucesso.");
        return usuarioMapper.toResponseList(usuariosAtivos);
    }
}