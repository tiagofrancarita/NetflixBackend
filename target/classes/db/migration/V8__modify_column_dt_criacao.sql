ALTER TABLE usuarios ADD COLUMN data_criacao DATETIME NULL;

UPDATE usuarios
SET data_criacao = NOW()
WHERE data_criacao IS NULL;

ALTER TABLE usuarios MODIFY COLUMN data_criacao DATETIME NOT NULL;