CREATE TABLE employees (
                           id UUID PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           store_id UUID NOT NULL,
                           created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                           CONSTRAINT fk_employee_store FOREIGN KEY (store_id) REFERENCES stores (id)
);

-- Index para agilizar a busca de funcionários por loja
CREATE INDEX idx_employees_store_id ON employees(store_id);