FROM ibm-semeru-runtimes:open-21-jdk-jammy AS builder

WORKDIR /workspace
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
# CAPA 1: POMs (se cachea si no cambian)
COPY pom.xml ./
COPY eureka-server/pom.xml ./eureka-server/
COPY gateway-service/pom.xml ./gateway-service/
#COPY authentication-service/pom.xml ./authentication-service/
COPY route-API/pom.xml ./route-API/
COPY location-API/pom.xml ./location-API/
# CAPA 2: Dependencias (se cachea)
RUN mvn dependency:go-offline -B -pl route-API -am

# CAPA 3: Código fuente (solo se invalida si cambia código)
COPY route-API/src ./route-API/src

# Copiar src de dependencias si hay inter-dependencias entre módulos
# COPY eureka-server/src ./eureka-server/src
# COPY gateway-service/src ./gateway-service/src
# ... solo si location-API depende de ellos

# CAPA 4: Build (solo el módulo necesario)
RUN mvn clean package -T 1C -pl route-API -am -DskipTests
# Stage 2: Runtime (Imagen final ligera)
FROM ibm-semeru-runtimes:open-21-jre-jammy

WORKDIR /app

# Crear usuario no-root para seguridad


# Copiar el JAR desde el builder
COPY --from=builder /workspace/route-API/target/*.jar app.jar
# Cambiar permisos
# Usar usuario no-root
# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8060/actuator/health || exit 1

# Exponer puerto
EXPOSE 8060
# JVM optimizations para contenedores
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-jar", \
    "app.jar"]