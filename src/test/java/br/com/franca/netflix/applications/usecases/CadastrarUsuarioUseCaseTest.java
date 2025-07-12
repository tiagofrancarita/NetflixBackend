package br.com.franca.netflix.applications.usecases;


import br.com.franca.netflix.application.usecase.CadastrarUsuarioUseCase;
import br.com.franca.netflix.domain.exception.RecursoDuplicadoException;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.domain.repository.UsuarioRepository;
import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import br.com.franca.netflix.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.franca.netflix.interfaces.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CadastrarUsuarioUseCaseTest {

    @Mock
    private UsuarioJpaRepository usuarioJpaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void CadastrarUsuarioComSucesso() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Tiago")
                .email("tiago@email.com")
                .senha("123456")
                .build();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("Tiago");
        usuarioEntity.setEmail("tiago@email.com");
        usuarioEntity.setSenha("123456");

        when(usuarioJpaRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioMapper.toEntity(usuario)).thenReturn(usuarioEntity);
        when(usuarioRepository.salvar(usuario)).thenReturn(usuario.toBuilder().id(1L).build());
        when(usuarioMapper.toDomain(usuarioEntity)).thenReturn(usuario.toBuilder().id(1L).build());

        // Act
        Usuario salvo = cadastrarUsuarioUseCase.executar(usuario);

        // Assert
        assertNotNull(salvo);
        assertEquals(1L, salvo.getId());
        assertEquals("Tiago", salvo.getNome());
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void CadastroEmailExistente() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Tiago")
                .email("ja@existe.com")
                .senha("123456")
                .build();

        when(usuarioJpaRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(RecursoDuplicadoException.class, () -> cadastrarUsuarioUseCase.executar(usuario));
        verify(usuarioJpaRepository, never()).save(any());
    }
}