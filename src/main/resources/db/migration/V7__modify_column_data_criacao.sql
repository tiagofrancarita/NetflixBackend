-- Passo 1: Preenche data_nascimento nula com uma data padrão (evita erro de NOT NULL)
UPDATE usuarios
SET data_nascimento = '2000-01-01 00:00:00'
WHERE data_nascimento IS NULL;

-- Passo 2: Agora sim, torna NOT NULL
ALTER TABLE usuarios
MODIFY COLUMN data_nascimento DATETIME NOT NULL;