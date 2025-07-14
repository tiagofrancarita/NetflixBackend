ALTER TABLE usuarios ADD COLUMN ativo CHAR(1) NOT NULL DEFAULT 'A';

UPDATE usuarios SET ativo = 'A' WHERE ativo IS NULL;