CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN', 'PROPIETARIO', 'ASISTENTE')),
    email VARCHAR(100) UNIQUE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS departamentos (
    id SERIAL PRIMARY KEY,
    codigo_dane VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS municipios (
    id SERIAL PRIMARY KEY,
    codigo_dane VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    departamento_id INTEGER NOT NULL REFERENCES departamentos(id)
);

CREATE TABLE IF NOT EXISTS veredas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    municipio_id INTEGER NOT NULL REFERENCES municipios(id)
);

CREATE TABLE IF NOT EXISTS propietarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS asistentes_tecnicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    tarjeta_profesional VARCHAR(50) UNIQUE,
    tipo VARCHAR(20) CHECK (tipo IN ('OFICIAL', 'PARTICULAR')),
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS predios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(150) NOT NULL,
    numero_registro VARCHAR(50) UNIQUE NOT NULL,
    area_hectareas NUMERIC(10, 2) NOT NULL,
    propietario_id UUID NOT NULL REFERENCES propietarios(id),
    vereda_id INTEGER NOT NULL REFERENCES veredas(id),
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS lugares_produccion (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(150) NOT NULL,
    numero_registro_ica VARCHAR(50) UNIQUE NOT NULL,
    propietario_id UUID NOT NULL REFERENCES propietarios(id),
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS especies_vegetales (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre_cientifico VARCHAR(150) NOT NULL,
    nombre_comun VARCHAR(100),
    densidad_siembra INTEGER,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS plagas (
    codigo VARCHAR(20) PRIMARY KEY,
    nombre_cientifico VARCHAR(150) NOT NULL,
    nombre_comun VARCHAR(100),
    descripcion TEXT,
    nivel_alerta VARCHAR(10) CHECK (nivel_alerta IN ('BAJO', 'MEDIO', 'ALTO'))
);

CREATE TABLE IF NOT EXISTS detalles_inspeccion (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    inspeccion_id UUID NOT NULL REFERENCES inspecciones_fitosanitarias(id),
    especie_vegetal_id INTEGER NOT NULL REFERENCES especies_vegetales(id),
    plaga_codigo VARCHAR(20) NOT NULL REFERENCES plagas(codigo),
    total_plantas INTEGER NOT NULL,
    plantas_afectadas INTEGER NOT NULL,
    nivel_incidencia NUMERIC(5, 2),
    observaciones TEXT
);

INSERT INTO usuarios (username, password_hash, rol, email) 
VALUES ('admin', 'admin123', 'ADMIN', 'admin@ica.gov.co')
ON CONFLICT DO NOTHING;

INSERT INTO departamentos (codigo_dane, nombre) VALUES
('05', 'Antioquia'),
('11', 'Bogotá D.C.'),
('76', 'Valle del Cauca'),
('25', 'Cundinamarca'),
('50', 'Meta')
ON CONFLICT DO NOTHING;

INSERT INTO municipios (codigo_dane, nombre, departamento_id) VALUES
('05001', 'Medellín', 1),
('11001', 'Bogotá', 2),
('76001', 'Cali', 3),
('25001', 'Agua de Dios', 4),
('50001', 'Villavicencio', 5)
ON CONFLICT DO NOTHING;

INSERT INTO veredas (nombre, municipio_id) VALUES
('El Poblado', 1),
('Chapinero', 2),
('San Antonio', 3),
('Centro', 4),
('Llanadas', 5)
ON CONFLICT DO NOTHING;

INSERT INTO especies_vegetales (codigo, nombre_cientifico, nombre_comun, densidad_siembra) VALUES
('FRT001', 'Musa paradisiaca', 'Banano', 1600),
('FRT002', 'Solanum lycopersicum', 'Tomate', 25000),
('FRT003', 'Coffea arabica', 'Café', 5000),
('VRT001', 'Solanum tuberosum', 'Papa', 40000)
ON CONFLICT DO NOTHING;

INSERT INTO plagas (codigo, nombre_cientifico, nombre_comun, descripcion, nivel_alerta) VALUES
('PLG001', 'Cosmopolites sordidus', 'Picudo negro del banano', 'Plaga del banano', 'ALTO'),
('PLG002', 'Tuta absoluta', 'Polilla del tomate', 'Plaga del tomate', 'MEDIO'),
('PLG003', 'Hypothenemus hampei', 'Broca del café', 'Plaga del café', 'ALTO'),
('PLG004', 'Phytophthora infestans', 'Tizón tardío', 'Enfermedad de la papa', 'ALTO')
ON CONFLICT DO NOTHING;

ALTER TABLE asistentes_tecnicos 
ADD COLUMN usuario_id UUID UNIQUE REFERENCES usuarios(id);

ALTER TABLE propietarios 
ADD COLUMN usuario_id UUID UNIQUE REFERENCES usuarios(id);

DROP TABLE IF EXISTS inspecciones_fitosanitarias CASCADE;

CREATE TABLE inspecciones_fitosanitarias (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lugar_produccion_id UUID NOT NULL REFERENCES lugares_produccion(id),
    asistente_tecnico_id UUID REFERENCES asistentes_tecnicos(id),
    propietario_id UUID NOT NULL REFERENCES propietarios(id),
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inspeccion DATE,
    estado VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'EN_PROCESO', 'COMPLETADA', 'CANCELADA')),
    observaciones TEXT,
    fecha_registro TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_inspeccion_estado ON inspecciones_fitosanitarias(estado);
CREATE INDEX idx_inspeccion_propietario ON inspecciones_fitosanitarias(propietario_id);
CREATE INDEX idx_inspeccion_asistente ON inspecciones_fitosanitarias(asistente_tecnico_id);
CREATE INDEX idx_inspeccion_lugar ON inspecciones_fitosanitarias(lugar_produccion_id);

CREATE TABLE lugar_especies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lugar_produccion_id UUID NOT NULL REFERENCES lugares_produccion(id),
    especie_vegetal_id INTEGER NOT NULL REFERENCES especies_vegetales(id),
    UNIQUE(lugar_produccion_id, especie_vegetal_id)
);
