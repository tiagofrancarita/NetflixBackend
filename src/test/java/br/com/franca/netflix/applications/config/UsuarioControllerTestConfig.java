package br.com.franca.netflix.applications.config;

import br.com.franca.netflix.application.usecase.CadastrarUsuarioUseCase;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UsuarioControllerTestConfig {

    @Bean
    public CadastrarUsuarioUseCase cadastrarUsuarioUseCase() {
        return Mockito.mock(CadastrarUsuarioUseCase.class);
    }

}
