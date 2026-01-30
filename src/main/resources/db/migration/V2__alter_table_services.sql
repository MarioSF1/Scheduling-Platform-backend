-- 1. Adiciona a coluna store_id se ela não existir
ALTER TABLE services ADD COLUMN IF NOT EXISTS store_id UUID NOT NULL;

-- 2. Cria a Foreign Key vinculando à tabela stores
ALTER TABLE services
    ADD CONSTRAINT fk_service_store
        FOREIGN KEY (store_id) REFERENCES stores (id);