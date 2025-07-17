package br.com.franca.netflix.security;

import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public CustomUserDetailsService(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email));

        var authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                .collect(Collectors.toSet());

        return new User(usuario.getEmail(), usuario.getSenha(), authorities);
    }
}