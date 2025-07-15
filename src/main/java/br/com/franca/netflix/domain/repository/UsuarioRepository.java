package br.com.franca.netflix.domain.repository;

import br.com.franca.netflix.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> buscarPorNome(String nome);
    Optional<Usuario> buscarPorCpf(String cpf);
    List<Usuario> listarTodos();
}