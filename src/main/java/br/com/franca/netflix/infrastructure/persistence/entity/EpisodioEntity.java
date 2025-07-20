package br.com.franca.netflix.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "episodios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private String titulo;

    private String descricao;

    private String duracao;

    @Column(name = "temporada_id", nullable = false)
    private Long temporadaId;

}
