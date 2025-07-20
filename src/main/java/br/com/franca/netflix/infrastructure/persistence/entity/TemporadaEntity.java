package br.com.franca.netflix.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "temporadas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private String titulo;

    private String descricao;

    @Column(name = "serie_id", nullable = false)
    private Long serieId;
}
