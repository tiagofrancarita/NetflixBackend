package br.com.franca.netflix.infrastructure.persistence.entity;

import br.com.franca.netflix.domain.enums.TipoMidia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "filmes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMidia midia;

    @Column(nullable = false)
    private Integer ano;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    @Column(nullable = false)
    private String imagemUrl;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private String duracao;

}
