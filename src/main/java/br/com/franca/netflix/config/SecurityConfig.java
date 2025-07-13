package br.com.franca.netflix.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

                http
                 .csrf(csrf -> csrf.disable()) //Desabilita csrf para facilitar testes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/usuarios/cadastrar", "/auth/login").permitAll() // libera acesso
                        .anyRequest().authenticated() // exige autenticacao para os demais
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Usa a chave secreta para validar o token JWT
        SecretKey secretKey = new SecretKeySpec("sua-chave-secreta-super-segura".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}