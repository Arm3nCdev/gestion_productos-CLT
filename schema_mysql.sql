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
    precio DECIMAL(12,0) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    estado VARCHAR(20) NOT NULL DEFAULT 'Activo'
);

-- Datos de prueba iniciales (Códigos numéricos y precios en Guaraníes Gs.)
INSERT INTO productos (codigo, nombre, categoria, precio, stock, estado) VALUES
('1001', 'Laptop Lenovo IdeaPad', 'Electrónica', 4500000, 8, 'Activo'),
('1002', 'Mouse Inalámbrico Logitech', 'Electrónica', 120000, 3, 'Activo'),
('1003', 'Teclado Mecánico RGB', 'Electrónica', 350000, 2, 'Activo'),
('1004', 'Silla Ergonómica Oficina', 'Hogar', 950000, 12, 'Activo'),
('1005', 'Cafetera Express', 'Hogar', 680000, 4, 'Inactivo');
