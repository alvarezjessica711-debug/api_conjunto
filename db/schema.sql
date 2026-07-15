CREATE DATABASE valle_san_remo;
USE valle_san_remo;
-- 1. Tabla de Roles (No depende de nadie)
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) UNIQUE NOT NULL
);

-- 2. Tabla de Apartamentos (No depende de nadie)
CREATE TABLE apartamentos (
    id_apartamento INT AUTO_INCREMENT PRIMARY KEY,
    torre VARCHAR(10) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    UNIQUE (torre, numero) 
);

-- 3. Tabla de Usuarios (Depende de roles)
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    documento VARCHAR(20) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 4. Tabla Intermedia: Usuarios - Apartamentos 
CREATE TABLE usuarios_apartamentos (
    id_usuario INT NOT NULL,
    id_apartamento INT NOT NULL,
    PRIMARY KEY (id_usuario, id_apartamento), 
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_apartamento) REFERENCES apartamentos(id_apartamento) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 5. Tabla de Paquetes (Depende de usuarios y de la tabla intermedia)
CREATE TABLE paquetes (
    id_paquete INT AUTO_INCREMENT PRIMARY KEY,
    empresa_transportadora VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    estado ENUM('Recibido en Porteria', 'Entregado al Residente') NOT NULL DEFAULT 'Recibido en Porteria',
    fecha_recepcion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega DATETIME NULL,
    id_usuario_destinatario INT NOT NULL,
    id_apartamento INT NOT NULL,
    id_usuario_registra INT NOT NULL,
    FOREIGN KEY (id_usuario_destinatario, id_apartamento) REFERENCES usuarios_apartamentos(id_usuario, id_apartamento) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_usuario_registra) REFERENCES usuarios(id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE
);