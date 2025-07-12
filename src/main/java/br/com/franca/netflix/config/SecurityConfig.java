package br.com.franca.netflix.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        return http
                .csrf(csrf -> csrf.disable()) //Desabilita csrf para facilitar testes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/usuarios/cadastrar").permitAll() // libera acesso
                        .anyRequest().authenticated() // exige autenticacao para os demais
                )
                .httpBasic(Customizer.withDefaults()) // Usa autenticação básica (atual forma recomendada)
                .build();
    }
}