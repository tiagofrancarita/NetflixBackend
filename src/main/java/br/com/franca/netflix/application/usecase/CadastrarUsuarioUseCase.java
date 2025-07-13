package br.com.franca.netflix.application.usecase;

import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.franca.netflix.interfaces.dto.UsuarioResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioJpaRepository usuarioJpaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario executar(Usuario request) {

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(senhaCriptografada)
                .build();

        Usuario salvo = usuarioRepository.salvar(usuario);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(salvo.getId());
        response.setNome(salvo.getNome());
        response.setEmail(salvo.getEmail());

        return usuario;
    }
}