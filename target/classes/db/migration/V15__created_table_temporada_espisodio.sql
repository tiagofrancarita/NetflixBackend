-- Tabela de Temporadas
CREATE TABLE temporadas (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            numero INT NOT NULL,
                            serie_id BIGINT NOT NULL, -- Só associar se for do tipo 'SERIE'
                            CONSTRAINT fk_temporada_serie
                                FOREIGN KEY (serie_id)
                                    REFERENCES filmes(id)
                                    ON DELETE CASCADE
);

-- Tabela de Episódios
CREATE TABLE episodios (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           titulo VARCHAR(255) NOT NULL,
                           descricao TEXT,
                           duracao VARCHAR(20) NOT NULL,
                           video_url VARCHAR(1000) NOT NULL,
                           temporada_id BIGINT NOT NULL,
                           CONSTRAINT fk_episodio_temporada
                               FOREIGN KEY (temporada_id)
                                   REFERENCES temporadas(id)
                                   ON DELETE CASCADE
);