# ====================
# STAGE 1: Base - Descargar dependencias
# ====================
FROM eclipse-temurin:21-jdk-alpine AS base
WORKDIR /workspace

COPY gateway-service/mvnw .
COPY gateway-service/.mvn .mvn
COPY eureka-server/mvnw .
COPY eureka-server/.mvn .mvn
COPY route-API/mvnw .
COPY route-API/.mvn .mvn
COPY account-API/mvnw .
COPY account-API/.mvn .mvn
COPY location-API/mvnw .
COPY location-API/.mvn .mvn
COPY authentication-service/mvnw .
COPY authentication-service/.mvn .mvn

# Copiar POM padre
COPY pom.xml .

# Copiar todos los POMs de módulos (para resolver dependencias)
COPY eureka-server/pom.xml eureka-server/pom.xml
COPY gateway-service/pom.xml gateway-service/pom.xml
COPY authentication-service/pom.xml authentication-service/pom.xml
COPY account-API/pom.xml account-API/pom.xml
COPY location-API/pom.xml location-API/pom.xml
COPY route-API/pom.xml route-API/pom.xml

# ====================
# STAGE 2: Builder - Construir todos los módulos
# ====================
FROM base AS builder
WORKDIR /workspace

# Copiar código fuente de todos los módulos
COPY eureka-server/src eureka-server/src
COPY gateway-service/src gateway-service/src
COPY authentication-service/src authentication-service/src
COPY account-API/src account-API/src
COPY location-API/src location-API/src
COPY route-API/src route-API/src

# Construir todos los módulos
RUN ./mvnw clean package -DskipTests -B -e


# ====================
# STAGE 3: Eureka Server Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS eureka-server
WORKDIR /app
COPY --from=builder /workspace/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]


# ====================
# STAGE 4: Gateway Service Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS gateway-service
WORKDIR /app
COPY --from=builder /workspace/gateway-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


# ====================
# STAGE 5: Authentication Service Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS authentication-service
WORKDIR /app
COPY --from=builder /workspace/authentication-service/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]


# ====================
# STAGE 6: Account API Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS account-api
WORKDIR /app
COPY --from=builder /workspace/account-API/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]


# ====================
# STAGE 7: Location API Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS location-api
WORKDIR /app
COPY --from=builder /workspace/location-API/target/*.jar app.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "app.jar"]


# ====================
# STAGE 8: Route API Runtime
# ====================
FROM eclipse-temurin:21-jre-alpine AS route-api
WORKDIR /app
COPY --from=builder /workspace/route-API/target/*.jar app.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "app.jar"]