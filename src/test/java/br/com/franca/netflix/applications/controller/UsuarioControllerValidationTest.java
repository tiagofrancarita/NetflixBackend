package br.com.franca.netflix.applications.controller;

import br.com.franca.netflix.application.usecase.CadastrarUsuarioUseCase;
import br.com.franca.netflix.applications.config.TestSecurityConfig;
import br.com.franca.netflix.applications.config.UsuarioControllerTestConfig;
import br.com.franca.netflix.domain.model.Usuario;
import br.com.franca.netflix.infrastructure.controller.UsuarioController;
import br.com.franca.netflix.interfaces.dto.UsuarioRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import({TestSecurityConfig.class, UsuarioControllerTestConfig.class}) // ✅ Usa a classe correta
public class UsuarioControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioRequest usuarioRequest;

    @Autowired
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;


    @BeforeEach
    void setUp() {
        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("Tiago");
        usuarioRequest.setEmail("tiago@email.com");
        usuarioRequest.setSenha("12345678");
    }

    @Test
    void deveRetornarErroQuandoNomeEstiverVazio() throws Exception {
        usuarioRequest.setNome("");
        mockMvc.perform(post("/usuarios/cadastrar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) content().string(containsString("O nome é obrigatório")));
    }

    @Test
    void deveRetornarErroQuandoEmailEstiverInvalido() throws Exception {
        usuarioRequest.setEmail("email_invalido");
        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("E-mail inválido")));
    }

    @Test
    void deveRetornarErroQuandoSenhaEstiverCurta() throws Exception {
        usuarioRequest.setSenha("123");
        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("A senha deve ter no minímo 8 caracteres")));
    }

    @Test
    void deveCadastrarUsuarioQuandoRequisicaoValida() throws Exception {
        Usuario usuarioSalvo = Usuario.builder()
                .id(1L)
                .nome("Tiago")
                .email("tiago@email.com")
                .senha("12345678")
                .build();

        Mockito.when(cadastrarUsuarioUseCase.executar(Mockito.any())).thenReturn(usuarioSalvo);

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Tiago"))
                .andExpect(jsonPath("$.email").value("tiago@email.com"));
    }
}