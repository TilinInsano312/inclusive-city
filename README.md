# inclusive-city
Un sistema de mapas que permite a los usuarios evaluar establecimientos según parámetros de accesibilidad general para generar un contraste de cliente/establecimiento.

## CI/CD

Se agregaron dos workflows en GitHub Actions:

- `CI` (`.github/workflows/ci.yml`): ejecuta `mvn clean verify` con Java 21 en cada `pull_request` y en pushes a `main`.
- `CD` (`.github/workflows/cd.yml`):
  - construye y publica imágenes Docker en GHCR para:
    - `eureka-server`
    - `gateway-service`
    - `route-api`
    - `location-api`
  - despliega a una VPS por SSH usando el mismo `docker-compose.yml` del repositorio.

## Secrets requeridos en GitHub

Configurar estos secretos en el repositorio:

- `VPS_HOST`: IP o dominio de la VPS
- `VPS_SSH_PORT`: puerto SSH (por ejemplo `22`)
- `VPS_SSH_USER`: usuario SSH
- `VPS_SSH_PRIVATE_KEY`: clave privada SSH para acceder a la VPS
- `VPS_DEPLOY_PATH`: ruta en la VPS donde vive el proyecto (debe contener `.env` y archivos montados)
- `GHCR_USERNAME`: username de GitHub con permiso de lectura de paquetes del repo en GHCR
- `GHCR_TOKEN`: token dedicado para la VPS con scope `read:packages` (solo pull desde GHCR)

## Notas de despliegue en VPS

- El workflow sincroniza `docker-compose.yml` y luego ejecuta:
  - `docker compose pull` de los servicios publicados en GHCR
  - `docker compose up -d --no-build` para levantar con imágenes remotas
- El `docker-compose.yml` mantiene `build` para uso local, y ahora también define `image` para despliegue con GHCR.
