FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /workspace
RUN apk add --no-cache maven

# CAPA 1: POMs (se cachea si no cambian)
COPY pom.xml ./
COPY eureka-server/pom.xml ./eureka-server/
COPY gateway-service/pom.xml ./gateway-service/
COPY authentication-service/pom.xml ./authentication-service/
COPY account-API/pom.xml ./account-API/
COPY route-API/pom.xml ./route-API/
COPY location-API/pom.xml ./location-API/
# CAPA 2: Dependencias (se cachea)
RUN mvn dependency:go-offline -B -pl gateway-service -am

# CAPA 3: Código fuente (solo se invalida si cambia código)
COPY gateway-service/src ./gateway-service/src

# Copiar src de dependencias si hay inter-dependencias entre módulos
# COPY eureka-server/src ./eureka-server/src
# COPY gateway-service/src ./gateway-service/src
# ... solo si location-API depende de ellos

# CAPA 4: Build (solo el módulo necesario)
RUN mvn clean package -pl gateway-service -am -DskipTests
# Stage 2: Runtime (Imagen final ligera)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 appgroup && \
    adduser -D -u 1001 -G appgroup appuser

# Copiar el JAR desde el builder
COPY --from=builder /workspace/gateway-service/target/*.jar app.jar
# Cambiar permisos
RUN chown appuser:appgroup app.jar
# Usar usuario no-root
USER appuser
# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Exponer puerto
EXPOSE 8080
# JVM optimizations para contenedores
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-jar", \
    "app.jar"]