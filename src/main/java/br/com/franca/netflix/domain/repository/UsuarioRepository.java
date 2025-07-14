package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorCpf(String cpf);
}