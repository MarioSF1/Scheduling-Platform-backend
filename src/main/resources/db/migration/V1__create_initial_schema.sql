-- Tabela de Usuários (Lojistas e Funcionários)
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_owner BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Lojas
CREATE TABLE stores (
    id UUID PRIMARY KEY,
    owner_id UUID REFERENCES users(id),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL, -- Para a URL: /minha-loja
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk_store_owner FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE INDEX idx_stores_slug ON stores(slug);

-- Tabela de Serviços
CREATE TABLE services (
    id UUID PRIMARY KEY,
    store_id UUID REFERENCES stores(id),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    duration_minutes INTEGER NOT NULL
);

-- Tabela de Disponibilidade (Carga Horária)
CREATE TABLE employee_schedules (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES users(id),
    day_of_week INTEGER NOT NULL, -- 1 (Segunda) a 7 (Domingo)
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);