-- =========================================================
-- Script SQL para SQL Server 2022
-- Proyecto: Gestión de Productos (Prueba Técnica Java Jr)
-- =========================================================

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'gestion_productos')
BEGIN
    CREATE DATABASE gestion_productos;
END
GO

USE gestion_productos;
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'productos')
BEGIN
    CREATE TABLE productos (
        id INT IDENTITY(1,1) PRIMARY KEY,
        codigo VARCHAR(50) NOT NULL UNIQUE,
        nombre VARCHAR(100) NOT NULL,
        categoria VARCHAR(50) NOT NULL,
        precio DECIMAL(10,2) NOT NULL,
        stock INT NOT NULL DEFAULT 0,
        estado VARCHAR(20) NOT NULL DEFAULT 'Activo'
    );
END
GO

-- Datos de prueba iniciales
IF NOT EXISTS (SELECT * FROM productos WHERE codigo = 'PROD001')
BEGIN
    INSERT INTO productos (codigo, nombre, categoria, precio, stock, estado) VALUES
    ('PROD001', 'Laptop Lenovo IdeaPad', 'Electrónica', 650.00, 10, 'Activo'),
    ('PROD002', 'Mouse Inalámbrico Logitech', 'Electrónica', 25.50, 3, 'Activo'),
    ('PROD003', 'Teclado Mecánico RGB', 'Electrónica', 75.00, 2, 'Activo'),
    ('PROD004', 'Silla Ergonómica Oficina', 'Hogar', 180.00, 8, 'Inactivo');
END
GO
