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

-- La tabla inicia vacía para que el usuario pueda insertar sus propios productos
