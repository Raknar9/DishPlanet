# Manual de Instalación y Despliegue de la Aplicación

Este manual detalla el procedimiento para la instalación y despliegue de la aplicación utilizando contenedores Docker para Tomcat y MySQL. Además, se describen las plataformas y programas requeridos.

## Requisitos

- Docker Desktop
- Maven
- IDE IntelliJ
- Código fuente del proyecto completo, incluyendo:
  - `Docker-compose.yml`
  - `Dockerfile`
  - Carpeta `mysql-init`

## Asegurarse de la Disponibilidad de Puertos

Verificar que los puertos `8081` y `3306` estén disponibles en su ordenador.

## Instalación en Entorno IntelliJ

1. **Abrir el Proyecto en IntelliJ:**
   - Asegúrate de tener el código fuente completo del proyecto.

2. **Desactivar los Tests en Maven:**
   - Desde el panel de control de Maven, desactiva los tests.

3. **Ejecutar el Package:**
   - Desde el mismo panel, ejecuta el comando `package`.

4. **Verificar la Generación de la Carpeta `target`:**
   - Comprueba que se ha generado la carpeta `target` en el proyecto.

5. **Ejecutar Docker Compose:**
   - Navega hasta el archivo `docker-compose.yml` y ejecuta ambos servicios:
     ```bash
     docker-compose up --build
     ```

6. **Verificar Contenedores en Marcha:**
   - Asegúrate de que los contenedores se han creado y están en marcha.

7. **Probar la Aplicación:**
   - Accede a la aplicación en `http://localhost:8081`.
   

## Instalación en Entorno CMD

1. **Asegurarse de la Disponibilidad de Puertos:**
   - Verificar que los puertos `8081` y `3306` estén disponibles en su ordenador.

2. **Código Fuente Completo:**
   - Asegúrate de tener el código fuente del proyecto completo, incluyendo `Docker-compose.yml`, `Dockerfile` y la carpeta `mysql-init`.

3. **Ejecutar Maven Package:**
   - Ubícate en el directorio raíz del proyecto y ejecuta:
     ```bash
     mvn clean package -DskipTests
     ```
   - Verifica que la carpeta `target` se ha generado en el directorio.

4. **Ejecutar Docker Compose:**
   - Mantente en el directorio raíz y ejecuta:
     ```bash
     docker-compose up --build
     ```

5. **Verificar Contenedores en Marcha:**
   - Asegúrate de que los contenedores están en marcha y la aplicación está disponible.

6. **Probar la Aplicación:**
   - Accede a la aplicación en `http://localhost:8081`.
