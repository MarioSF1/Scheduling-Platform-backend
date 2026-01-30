CREATE TABLE work_schedules (
    id UUID PRIMARY KEY,
    employee_id UUID NOT NULL,
    day_of_week INTEGER NOT NULL, -- 1 (Segunda) a 7 (Domingo)
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    lunch_start TIME, -- Opcional: Horário de almoço
    lunch_end TIME,
    CONSTRAINT fk_schedule_employee FOREIGN KEY (employee_id) REFERENCES employees (id),
    UNIQUE(employee_id, day_of_week) -- Um funcionário tem 1 config por dia
);