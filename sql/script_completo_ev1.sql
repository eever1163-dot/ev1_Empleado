-- ====================================================================
-- Script COMPLETO - Base de datos ev1
-- Tablas: EMPLEADO y CATEGORIA (relacionadas por llave foranea)
-- ====================================================================

CREATE DATABASE IF NOT EXISTS `ev1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ev1`;

-- Se eliminan las tablas si ya existian, en el orden correcto
-- (primero empleado porque depende de categoria con la llave foranea)
DROP TABLE IF EXISTS `empleado`;
DROP TABLE IF EXISTS `categoria`;

-- --------------------------------------------------------------------
-- Tabla CATEGORIA (tabla de TIPO/CATEGORIA)
-- --------------------------------------------------------------------
CREATE TABLE `categoria` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `categoria` (`descripcion`) VALUES
('Operativo'),
('Administrativo'),
('Gerencial'),
('Practicante'),
('Contratista');

-- --------------------------------------------------------------------
-- Tabla EMPLEADO (con 10 campos, incluida la llave foranea id_categoria)
-- --------------------------------------------------------------------
CREATE TABLE `empleado` (
  `id_empleado` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `documento` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `correo` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cargo` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_categoria` int NOT NULL,
  `salario` decimal(10,2) NOT NULL,
  `fecha_contratacion` date NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_empleado`),
  UNIQUE KEY `documento` (`documento`),
  UNIQUE KEY `correo` (`correo`),
  KEY `fk_empleado_categoria_idx` (`id_categoria`),
  CONSTRAINT `fk_empleado_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `empleado` (`nombre`,`apellido`,`documento`,`correo`,`telefono`,`cargo`,`id_categoria`,`salario`,`fecha_contratacion`,`activo`) VALUES
('Lucía','Ramírez','78451236','lucia.ramirez@example.com','555-384-7291','Analista de Sistemas',1,2850.75,'2024-08-19',1);
