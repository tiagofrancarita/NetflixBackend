-- Histórico de visualização por perfil
CREATE TABLE historico_visualizacao (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        perfil_id BIGINT NOT NULL,
                                        filme_id BIGINT NOT NULL,
                                        data_visualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        progresso INT DEFAULT 0, -- percentual
                                        status VARCHAR(20) DEFAULT 'INICIADO',
                                        CONSTRAINT fk_hist_perfil FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE,
                                        CONSTRAINT fk_hist_filme FOREIGN KEY (filme_id) REFERENCES filmes(id) ON DELETE CASCADE
);