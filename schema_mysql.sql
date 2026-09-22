-- =========================================================
-- Script SQL para MySQL / MariaDB
-- Proyecto: Gestión de Productos (Prueba Técnica Java Jr)
-- =========================================================

CREATE DATABASE IF NOT EXISTS gestion_productos;
USE gestion_productos;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    estado VARCHAR(20) NOT NULL DEFAULT 'Activo'
);

-- Datos de prueba iniciales
INSERT INTO productos (codigo, nombre, categoria, precio, stock, estado) VALUES
('PROD001', 'Laptop Lenovo IdeaPad', 'Electrónica', 650.00, 10, 'Activo'),
('PROD002', 'Mouse Inalámbrico Logitech', 'Electrónica', 25.50, 3, 'Activo'),
('PROD003', 'Teclado Mecánico RGB', 'Electrónica', 75.00, 2, 'Activo'),
('PROD004', 'Silla Ergonómica Oficina', 'Hogar', 180.00, 8, 'Inactivo');
