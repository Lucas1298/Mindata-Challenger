# Challenge Mindata App

Este proyecto es una aplicación desarrollada en Java 21 con Spring Boot, la cual se encuentra dockerizada y utiliza PostgreSQL, Kafka, Redis y Zookeeper como parte de su infraestructura.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot**
- **Docker & Docker Compose**
- **PostgreSQL**
- **Kafka**
- **Zookeeper**
- **Redis**

## Configuración y Uso

### Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalados:

- **Docker** y **Docker Compose**

### Instalación y Ejecución

1. Clona el repositorio:

   ```sh
   git clone https://github.com/Lucas1298/Mindata-Challenger.git
   cd Mindata-Challenger
   ```

2. Construye y levanta los contenedores con Docker Compose:

   ```sh
   docker compose up
   ```

   Esto iniciará los siguientes servicios:

    - PostgreSQL en `localhost:5432`
    - Zookeeper en `localhost:22181`
    - Kafka en `localhost:29092`
    - Redis en `localhost:6379`
    - Aplicación Spring Boot en `localhost:8080`

## Variables de Entorno

El archivo `docker-compose.yml` define las siguientes variables de entorno:

| Variable                  | Descripción                    |
| ------------------------- | ------------------------------ |
| `DB_URL`                  | URL de conexión a PostgreSQL   |
| `DB_USER`                 | Usuario de la base de datos    |
| `DB_PASS`                 | Contraseña de la base de datos |
| `KAFKA_BOOTSTRAP_SERVERS` | Servidor de Kafka              |
| `KAFKA_CONSUMER_GROUP`    | Grupo de consumidores de Kafka |
| `REDIS_HOST`              | Host de Redis                  |
| `REDIS_PORT`              | Puerto de Redis                |


## Autenticación y Seguridad

La aplicación cuenta con un sistema de autenticación basado en JWT. Se pueden utilizar los siguientes endpoints:

### Crear un nuevo usuario

`POST /auth/addNewUser`

**Request Body:**
```json
{
  "username": "usuarioEjemplo",
  "password": "contraseñaSegura"
}
```

### Generar un token de autenticación

`POST /auth/generateToken`

**Request Body:**
```json
{
  "username": "usuarioEjemplo",
  "password": "contraseñaSegura"
}
```

**Response:**
```json
{
  "token": "jwt-token-generado"
}
```

## Documentación de la API

- **Swagger**: Accede a la documentación en `http://localhost:8080/swagger-ui/index.html#/`.
- **Postman**: Se ha incluido una colección de Postman en el repositorio para facilitar las pruebas. Puedes importarla en Postman y utilizar los endpoints de manera rápida. Se encuentra en resources
