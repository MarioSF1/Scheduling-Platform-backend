CREATE TABLE appointments (
      id UUID PRIMARY KEY,
      store_id UUID NOT NULL,
      employee_id UUID NOT NULL,
      customer_id UUID NOT NULL,
      service_id UUID NOT NULL,
      start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      status VARCHAR(20) NOT NULL, -- PENDING, CONFIRMED, CANCELLED, COMPLETED
      created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      CONSTRAINT fk_app_store FOREIGN KEY (store_id) REFERENCES stores (id),
      CONSTRAINT fk_app_employee FOREIGN KEY (employee_id) REFERENCES employees (id),
      CONSTRAINT fk_app_customer FOREIGN KEY (customer_id) REFERENCES users (id),
      CONSTRAINT fk_app_service FOREIGN KEY (service_id) REFERENCES services (id)
);

CREATE INDEX idx_app_employee_date ON appointments(employee_id, start_time);