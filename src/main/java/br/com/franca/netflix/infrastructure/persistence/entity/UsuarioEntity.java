package br.com.franca.netflix.infrastructure.persistence.entity;

import br.com.franca.netflix.domain.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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