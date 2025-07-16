CREATE TABLE refresh_tokens (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                token VARCHAR(255) NOT NULL,
                                email VARCHAR(255) NOT NULL,
                                data_expiracao DATETIME NOT NULL
);