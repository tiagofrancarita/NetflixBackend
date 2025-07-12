package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.exception.RecursoDuplicadoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;


    public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    public Usuario executar(Usuario usuario) {

        /*
        String nome = usuario.getNome();
        // Simula NullPointer
        if (nome.equals("nulo")) {
            Object obj = null;
            obj.toString(); // dispara NullPointerException
        }*/

        if (usuarioJpaRepository.existsByEmail(usuario.getEmail())) {
            throw new RecursoDuplicadoException("email", usuario.getEmail());
        }

        return usuarioRepository.salvar(usuario);
    }
}