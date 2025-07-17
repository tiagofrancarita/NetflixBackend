package br.com.franca.netflix.infrastructure.persistence.entity;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;
    private String senha;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataCriacao;

    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ativo", nullable = false, length = 1)
    private StatusUsuario ativo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @PrePersist
    public void prePersist(){

        if (this.dataCriacao == null){
            this.dataCriacao = LocalDateTime.now();
        }
        if (this.ativo == null) {
            this.ativo = StatusUsuario.A;
        }
    }
}