# API del Condominio Valle San Remo

Este proyecto es una API REST básica desarrollada en Java con Maven para gestionar paquetes, usuarios y apartamentos de un condominio. La aplicación se conecta a una base de datos MySQL y expone endpoints para realizar operaciones CRUD sobre paquetes, además de consultar usuarios y apartamentos.

## Características

- Conexión a MySQL mediante JDBC
- Arquitectura simple en capas:
  - modelo
  - dao
  - conexion
  - vista
- API REST con servidor HTTP integrado en Java
- Soporte para operaciones básicas de paquetes

## Estructura del proyecto

```text
src/
├── com/mycompany/api_conjunto/
│   └── Api_conjunto.java
├── conexion/
│   └── ConexionBD.java
├── dao/
│   ├── ApartamentoDAO.java
│   ├── PaqueteDAO.java
│   └── UsuarioDAO.java
├── modelo/
│   ├── Apartamento.java
│   ├── Paquete.java
│   └── Usuario.java
└── vista/
    └── Principal.java
```

## Tecnologías utilizadas

- Java 17+
- Maven
- MySQL Connector/J
- JDBC

## Requisitos

- Java 17 o superior
- Maven instalado o uso de una distribución portable
- Servidor MySQL en ejecución
- Base de datos llamada `valle_san_remo`

## Base de datos

La API espera una base de datos MySQL con el esquema que incluye las tablas:

- `roles`
- `apartamentos`
- `usuarios`
- `usuarios_apartamentos`
- `paquetes`

Asegúrate de que la conexión esté configurada correctamente en [src/conexion/ConexionBD.java](src/conexion/ConexionBD.java).

### Credenciales por defecto

Actualmente la configuración usa:

- URL: `jdbc:mysql://localhost:3306/valle_san_remo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
- Usuario: `root`
- Contraseña: `Admin1234`

Si tu instalación de MySQL usa otras credenciales, ajústalas en el archivo de conexión.

## Configuración del proyecto

El archivo [pom.xml](pom.xml) incluye:

- `mysql-connector-j` como dependencia
- `sourceDirectory` apuntando a `src`
- `maven.compiler.release` configurado en Java 17

## Ejecutar el proyecto

### 1. Compilar

```bash
mvn clean compile
```

### 2. Ejecutar la API

```bash
mvn exec:java -Dexec.mainClass=com.mycompany.api_conjunto.Api_conjunto
```

O bien, si prefieres ejecutar la clase principal directamente:

```bash
java --add-modules jdk.httpserver -cp target/classes com.mycompany.api_conjunto.Api_conjunto
```

La API quedará disponible en:

```text
http://localhost:8080
```

## Endpoints disponibles

### Paquetes

- `GET /api/paquetes` → listar todos los paquetes
- `GET /api/paquetes/{id}` → obtener un paquete por ID
- `POST /api/paquetes` → crear un paquete
- `PUT /api/paquetes/{id}` → actualizar un paquete
- `DELETE /api/paquetes/{id}` → eliminar un paquete

### Usuarios

- `GET /api/usuarios` → listar usuarios

### Apartamentos

- `GET /api/apartamentos` → listar apartamentos

## Ejemplo de solicitud para crear un paquete

```bash
curl -X POST http://localhost:8080/api/paquetes \
  -H "Content-Type: application/json" \
  -d '{
    "empresaTransportadora": "Interrapidisimo",
    "descripcion": "Paquete de documentos",
    "estado": "Recibido en Porteria",
    "idUsuarioDestinatario": 1,
    "idApartamento": 1,
    "idUsuarioRegistra": 1
  }'
```

## Ejemplo de consulta

```bash
curl http://localhost:8080/api/paquetes
```

## Notas importantes

- La implementación actual es una base funcional para demostrar la conexión y el consumo de datos desde una API REST.
- Si la base de datos no está configurada correctamente, la API responderá con un error de conexión.
- Para una versión más completa, se puede ampliar con autenticación, validaciones y respuestas JSON más ricas.

## Próximos pasos recomendados

- Agregar validaciones de entrada
- Implementar respuestas JSON más completas
- Crear endpoints para roles y relación usuarios-apartamentos
- Añadir documentación con Swagger/OpenAPI
- Mejorar el manejo de errores y excepciones

