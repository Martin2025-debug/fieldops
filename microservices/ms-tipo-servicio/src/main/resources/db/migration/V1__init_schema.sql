CREATE TABLE IF NOT EXISTS tipos_servicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(40) NOT NULL,
    referencia_externa_id BIGINT NULL,
    fecha_creacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS tipos_servicio_detalles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    tipos_servicio_id BIGINT NOT NULL,
    CONSTRAINT fk_tipos_servicio_detalles_tipos_servicio FOREIGN KEY (tipos_servicio_id) REFERENCES tipos_servicio(id) ON DELETE CASCADE
);