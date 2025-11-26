
-- Crear tabla de usuarios manualmente para control total del esquema
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- INSERTAR USUARIOS DE PRUEBA
INSERT INTO usuarios (username, password, enabled, email) VALUES
('luis', '$2a$10$biNjUd2t8f2PXlcD7txFi.nO0nCtWXzPhJyYMmOMK5z.BrTXpg4sm', true, 'luis@gmail.com');
--password: luis123

INSERT INTO usuarios (username, password, enabled, email) VALUES
('marisol', '$2a$10$yOuDJ4Hpio3Y1KD6hYJEZOWJidy7PWXPO8r.AjGsG7CvMLH4E4STu', true, 'marisol@gmail.com');
--password: mari123