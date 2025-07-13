package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
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
                .build();


    }
}