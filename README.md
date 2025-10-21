# 📝 CRUD Personas - Docker Setup

## 🔹 Pre-requisitos

Antes de levantar el proyecto, asegúrate de que tu máquina tenga:

1. **Docker Desktop** instalado y corriendo
    - [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
    - Versiones recientes incluyen Docker CLI y Docker Compose integrado.
2. Terminal en macOS/Linux o PowerShell/WSL en Windows.
3. Puerto `8080` y `5432` libres para la app y la base de datos.

> ⚠️ Nota: Para desarrollo, la compilación se hace dentro del contenedor usando Maven Wrapper, por lo que **no necesitas tener Java ni Maven instalados en tu máquina**.

---

## 🔹 Consideraciones

- El proyecto usa **Docker Compose** para levantar dos servicios:
    1. `db` → PostgreSQL con persistencia de datos.
    2. `app` → Spring Boot CRUD Personas.
- Las variables de conexión a la base y CORS están definidas en `docker-compose.yml`.
- La primera vez que se levanta el proyecto, Docker descargará las imágenes y construirá la app, lo que puede tardar unos minutos.

---

## 🔹 Ejecutar el proyecto

### 1. Levantar el proyecto (construyendo imágenes)
Ejecuta este comando en la raiz del proyecto, construye la app y la base de datos, mostrando logs en tiempo real
```bash
docker compose up --build
```

### 2. Detener el proyecto
```bash
docker compose down
```

## 🔹 Acceso a la aplicación
- API base:
  http://localhost:8080/api/previred/person
- URL de documentacion Swagger:
  http://localhost:8080/api/previred/docs