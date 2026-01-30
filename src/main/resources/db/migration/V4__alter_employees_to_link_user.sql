-- 1. Removemos a coluna name, pois o nome virá da tabela users
ALTER TABLE employees DROP COLUMN name;

-- 2. Adicionamos a coluna user_id
ALTER TABLE employees ADD COLUMN user_id UUID NOT NULL;

-- 3. Criamos a Foreign Key para a tabela users
ALTER TABLE employees
    ADD CONSTRAINT fk_employee_user
        FOREIGN KEY (user_id) REFERENCES users (id);

-- 4. Adicionamos uma restrição única para evitar que o mesmo user
-- seja cadastrado duas vezes na mesma loja
ALTER TABLE employees ADD CONSTRAINT uq_user_store UNIQUE (user_id, store_id);