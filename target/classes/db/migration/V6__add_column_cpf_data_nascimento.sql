-- Adiciona os campos como NULL primeiro
ALTER TABLE usuarios
    ADD COLUMN cpf VARCHAR(14) NULL;

ALTER TABLE usuarios
    ADD COLUMN data_nascimento DATETIME NULL;

-- Atualiza registros antigos com CPFs provisórios válidos
UPDATE usuarios
SET cpf = CONCAT('000.000.000-', LPAD(id, 2, '0'));

-- Agora sim, aplica a restrição de NOT NULL e UNIQUE
ALTER TABLE usuarios
    MODIFY COLUMN cpf VARCHAR(14) NOT NULL UNIQUE;