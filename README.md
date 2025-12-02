# Auth API - Sistema de Autenticación JWT

## Descripción

API REST de autenticación desarrollada con Spring Boot que implementa seguridad basada en JSON Web Tokens (JWT). Permite autenticar usuarios contra una base de datos H2 en memoria y genera tokens JWT para acceso a recursos protegidos.

## Tecnologías

- Java 11
- Spring Boot 2.3.12
- Spring Security
- Spring Data JPA
- H2 Database (en memoria)
- JJWT 0.9.1

## Estructura del Proyecto

```
src/main/java/com/dimension/
├── AuthApplication.java
└── security/
    ├── SecurityConfig.java
    ├── controller/
    │   └── AuthController.java
    ├── dto/
    │   ├── JWTAuthResponseDTO.java
    │   └── LoginDTO.java
    ├── jwt/
    │   ├── JwtAuthenticationEntryPoint.java
    │   ├── JwtAuthenticationFilter.java
    │   └── JwtTokenProvider.java
    ├── model/
    │   └── Usuario.java
    ├── repository/
    │   └── IUsuarioRepository.java
    └── service/
        └── JpaUserDetailsService.java
```

## Configuración

El proyecto utiliza las siguientes propiedades configurables en `application.yml`:

| Propiedad | Valor | Descripción |
|-----------|-------|-------------|
| server.port | 8090 | Puerto del servidor |
| app.jwt-secret | JWTSecretKey | Clave secreta para firmar tokens |
| app.jwt-expiration-milliseconds | 604800000 | Tiempo de expiración del token (7 días) |

## Endpoints

### Autenticación

**POST** `/api/auth/login`

Request body:
```json
{
  "email": "luis@gmail.com",
  "password": "luis123"
}
```

Response:
```json
{
  "tokenDeAcceso": "eyJhbGciOiJIUzUxMiJ9...",
  "tipoDeToken": "Bearer"
}
```

## Usuarios de Prueba

| Email | Contraseña |
|-------|------------|
| luis@gmail.com | luis123 |
| marisol@gmail.com | mari123 |

## Acceso a la Consola H2

La base de datos H2 incluye una consola web para visualizar y manipular los datos.

**URL de acceso:** `http://localhost:8090/h2-console`

**Parámetros de conexión:**

| Campo | Valor |
|-------|-------|
| Driver Class | org.h2.Driver |
| JDBC URL | jdbc:h2:mem:authdb |
| User Name | sa |
| Password | (dejar vacío) |

Una vez conectado, se puede consultar la tabla `usuarios` con:

```sql
SELECT * FROM usuarios;
```

## Ejecución

### Con Maven

```bash
mvn spring-boot:run
```

### Compilar y ejecutar JAR

```bash
mvn clean package
java -jar target/auth-api-0.0.1-SNAPSHOT.jar
```

## Uso del Token JWT

Una vez obtenido el token mediante el endpoint de login, debe incluirse en las peticiones a recursos protegidos mediante el header `Authorization`:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```
