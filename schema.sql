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
        precio DECIMAL(12,0) NOT NULL,
        stock INT NOT NULL DEFAULT 0,
        estado VARCHAR(20) NOT NULL DEFAULT 'Activo'
    );
END
GO

-- La tabla inicia vacía para que el usuario pueda insertar sus propios productos
