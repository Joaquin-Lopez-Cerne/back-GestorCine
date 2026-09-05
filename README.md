# Gestor de Cine

Gestor de Cine es una API REST desarrollada con Java y Spring Boot para administrar usuarios y películas.
Permite registrar, consultar, actualizar y eliminar información mediante endpoints HTTP.
Los usuarios pueden asociarse con películas y mantener una lista de películas favoritas.
Los datos se almacenan de forma persistente en una base de datos MySQL.
El alcance actual comprende el backend y sus operaciones principales, sin incluir una interfaz gráfica.

## Funcionalidades

- CRUD de usuarios y películas.
- Registro de usuarios con nombre, email, fecha de nacimiento y sexo.
- Relaciones `OneToMany` y `ManyToMany` entre usuarios y películas.
- Uso de DTO para recibir y devolver datos de usuarios.
- Respuestas HTTP mediante `ResponseEntity`.
- Manejo global de excepciones para recursos inexistentes y argumentos inválidos.

## Arquitectura

El proyecto está organizado en las capas `Controller`, `Service`, `Repository` y `Model`. También incluye los paquetes `Dto` para la transferencia de datos y `Exception` para el manejo centralizado de errores.

## Endpoints principales

| Método |      Endpoint        |    Función    |
| ---    | --- | ---| ---       |---| --- | --- | 
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

## Ejemplo de registro de usuario

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "fechaNacimiento": "2000-05-15",
  "sexo": "MASCULINO"
}
```

La solicitud se realiza mediante `POST` a `http://localhost:8080/api/usuarios`.

## Ejecución

1. Iniciar MySQL y crear la base de datos `cine_db`.
2. Verificar el usuario y la contraseña en `application.properties`.
3. Ejecutar `ECommerceApplication` desde IntelliJ IDEA.
4. Probar los endpoints con Postman o `curl`.
