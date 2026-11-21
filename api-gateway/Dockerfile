# Build stage
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy root pom and module sources
COPY pom.xml .
COPY api-gateway/ api-gateway/
COPY proto-common/pom.xml proto-common/
COPY student-service/pom.xml student-service/
COPY grade-service/pom.xml grade-service/
COPY analytics-service/pom.xml analytics-service/

# Package api-gateway
RUN mvn clean package -pl api-gateway -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN apk add --no-cache wget curl
COPY --from=builder /app/api-gateway/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
