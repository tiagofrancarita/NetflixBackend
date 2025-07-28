package br.com.franca.netflix.config;


import br.com.franca.netflix.security.CustomAccessDeniedHandler;
import br.com.franca.netflix.security.CustomUserDetailsService;
import br.com.franca.netflix.security.JwtAuthenticationFilter;
import br.com.franca.netflix.security.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtProperties jwtProperties;
    private final RequestLoggingFilter requestLoggingFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtProperties jwtProperties, RequestLoggingFilter requestLoggingFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtProperties = jwtProperties;
        this.requestLoggingFilter = requestLoggingFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

                http
                        .cors(Customizer.withDefaults())
                        .exceptionHandling(exception -> exception
                                .accessDeniedHandler(customAccessDeniedHandler)
                        )
                 .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/auth/login",
                                        "/auth/refresh",
                                        "/auth/logout",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/webjars/**"
                                ).permitAll()
                                .requestMatchers("/usuarios/cadastrarUsuario").permitAll()
                                .requestMatchers("/actuator/prometheus").permitAll()
                                .requestMatchers("/usuarios/listarTodos").hasRole("ADMIN") // só ADMIN pode listar todos
                                .requestMatchers("/usuarios/buscarPorId/**").hasAnyRole("ADMIN", "USER") // ADMIN e USER
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}