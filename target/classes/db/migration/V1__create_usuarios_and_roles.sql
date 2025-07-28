-- Tabela de usuários
CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          cpf VARCHAR(100) NOT NULL UNIQUE,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          data_nascimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          ativo CHAR(1) NOT NULL  DEFAULT 'A'
);

-- Tabela de roles (ex: ADMIN, USER)
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(50) NOT NULL UNIQUE
);

-- Relacionamento N:N entre usuários e roles
CREATE TABLE usuario_roles (
                               usuario_id BIGINT NOT NULL,
                               role_id BIGINT NOT NULL,
                               PRIMARY KEY (usuario_id, role_id),
                               CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                               CONSTRAINT fk_usuario_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
