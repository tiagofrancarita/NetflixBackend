package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .cpf(entity.getCpf())
                .dataNascimento(entity.getDataNascimento())
                .dataCriacao(entity.getDataCriacao())
                .ativo(entity.getAtivo())
                .build();
    }

    public UsuarioEntity toEntity(Usuario usuario){
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .cpf(usuario.getCpf())
                .dataNascimento(usuario.getDataNascimento())
                .dataCriacao(usuario.getDataCriacao())
                .ativo(usuario.getAtivo())
                .build();
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setAtivo(usuario.getAtivo()); // ou getStatus() se tiver renomeado
        response.setCpf(usuario.getCpf());
        response.setDataNascimento(usuario.getDataNascimento());
        response.setDataCriacao(usuario.getDataCriacao());
        return response;
    }
}