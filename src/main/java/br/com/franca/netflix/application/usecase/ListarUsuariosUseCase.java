package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.franca.netflix.infrastructure.persistence.specification.UsuarioSpecification;
import br.com.franca.netflix.interfaces.dto.PaginacaoDTO;
import br.com.franca.netflix.interfaces.dto.UsuarioFiltroDTO;
import br.com.franca.netflix.interfaces.dto.PageDTO;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarUsuariosUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ListarUsuariosUseCase.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public PageDTO<UsuarioResponse> listarComFiltroPaginacao(UsuarioFiltroDTO filtro, PaginacaoDTO paginacao) {

        Specification<UsuarioEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
            spec = spec.and(UsuarioSpecification.nomeContem(filtro.getNome()));
        }

        if (filtro.getAtivo() != null) {
            spec = spec.and(UsuarioSpecification.statusIgual(filtro.getAtivo()));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(paginacao.getDirecao()), paginacao.getOrdenacao());
        Pageable pageable = PageRequest.of(paginacao.getPagina(), paginacao.getTamanho(), sort);

        Page<UsuarioEntity> page = ((UsuarioJpaRepository)usuarioRepository).findAll(spec, pageable);

        List<UsuarioResponse> content = page.stream()
                .map(usuarioMapper::toDomain)
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());

        return new PageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
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