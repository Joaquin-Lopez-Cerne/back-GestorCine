# Gestor de Cine

Gestor de Cine es una API REST desarrollada con Java y Spring Boot para administrar usuarios y películas.
Permite registrar, consultar, actualizar y eliminar información mediante endpoints HTTP.
Los usuarios pueden asociarse con películas y mantener una lista de películas favoritas.
Los datos se almacenan de forma persistente en una base de datos MySQL.
El alcance actual comprende el backend y sus operaciones principales, sin incluir una interfaz gráfica.

## Funcionalidades

- CRUD de usuarios y películas.
- Registro de usuarios con nombre, email, contraseña, fecha de nacimiento y sexo.
- Relaciones `OneToMany` y `ManyToMany` entre usuarios y películas.
- Uso de DTO para recibir y devolver datos de usuarios y películas.
- DTO específicos para películas, registro e inicio de sesión.
- Respuestas HTTP mediante `ResponseEntity`.
- Manejo global de excepciones para recursos inexistentes y argumentos inválidos.
- Autenticación stateless con Spring Security y JWT.
- Contraseñas cifradas con BCrypt.
- Roles `USER` y `ADMIN` para controlar operaciones protegidas.

## Arquitectura

El proyecto está organizado en las capas `Controller`, `Service`, `Repository` y `Model`. También incluye los paquetes `Dto` para la transferencia de datos y `Exception` para el manejo centralizado de errores.

## Endpoints principales

| Método | Endpoint | Función |
| --- | --- | --- |
| GET    | `/api/usuarios`      | Listar usuarios |
| GET    | `/api/usuarios/{id}` | Buscar un usuario |
| POST   | `/api/usuarios`      | Registrar un usuario |
| PUT    | `/api/usuarios/{id}` | Actualizar un usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar un usuario |
| GET    | `/api/peliculas`     | Listar películas |
| GET    | `/api/peliculas/{id}`| Buscar una película |
| POST   | `/api/peliculas`     | Crear una película |
| PUT    | `/api/peliculas/{id}`| Actualizar una película |
| DELETE | `/api/peliculas/{id}`| Eliminar una película |

## Autenticación y autorización

El alta y el inicio de sesión se realizan mediante los endpoints públicos de `/api/auth`.
Las contraseñas se guardan cifradas con BCrypt. Cuando el login es correcto, el backend devuelve
un JWT. En los endpoints protegidos el cliente debe enviarlo en el header `Authorization` como
`Bearer <token>`. La API trabaja de forma stateless y no guarda una sesión HTTP del usuario.

| Método | Endpoint | Acceso | Función |
| --- | --- | --- | --- |
| POST | `/api/auth/register` | Público | Registrar un usuario con rol `USER` |
| POST | `/api/auth/login` | Público | Validar email y contraseña y devolver un JWT |
| GET | `/api/peliculas/**` | Público | Consultar películas |
| POST/PUT/DELETE | `/api/peliculas/**` | `ADMIN` | Administrar películas |
| DELETE | `/api/usuarios/**` | `ADMIN` | Eliminar usuarios |
| Resto de endpoints | Autenticado | Requiere un JWT válido |

Ejemplo de registro:

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "password": "una-clave-segura",
  "fechaNacimiento": "2000-05-15",
  "sexo": "MASCULINO"
}
```

La solicitud se realiza mediante `POST` a `http://localhost:8080/api/auth/register`.
Luego se puede iniciar sesión con `POST /api/auth/login`:

```json
{
  "email": "juan@email.com",
  "password": "una-clave-segura"
}
```

Si las credenciales son correctas, la respuesta es el token JWT. Para consumir un endpoint protegido:

```http
Authorization: Bearer <token>
```

Para crear un administrador, registrá primero el usuario y cambiá su columna `role` a
`ADMIN` en la base de datos. El endpoint público de registro nunca permite autoasignarse
ese rol.

## DTO de películas

Los endpoints de películas trabajan con DTO en lugar de exponer directamente la entidad `Pelicula`:

- `PeliculaDTO`: se usa para crear y devolver películas.
- `PeliculaUpdateDTO`: se usa para actualizar los datos de una película.

Ejemplo para crear una película:

```json
{
  "titulo": "Interestelar",
  "genero": "Ciencia ficción",
  "duracion": 169
}
```

## Ejemplo de alta de usuario mediante UsuarioRequestDTO

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "fechaNacimiento": "2000-05-15",
  "sexo": "MASCULINO"
}
```

## Ejecución

1. Iniciar MySQL y crear la base de datos configurada en `application.properties`.
2. Verificar el usuario y la contraseña de MySQL en `application.properties`.
3. Ejecutar `ECommerceApplication` desde IntelliJ IDEA.
4. Probar los endpoints con Postman o `curl`.
