-- Categorias de filmes (ex: Ação, Comédia)
CREATE TABLE categorias (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL UNIQUE
);

-- Filmes
CREATE TABLE filmes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        titulo VARCHAR(255) NOT NULL,
                        descricao TEXT,
                        imagem_url VARCHAR(500),
                        video_url VARCHAR(500),
                        ano INT,
                        duracao INT, -- em minutos
                        categoria_id BIGINT,
                        CONSTRAINT fk_filme_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);