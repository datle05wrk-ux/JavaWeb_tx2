# Build stage
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy root pom and module sources
COPY pom.xml .
COPY proto-common/ proto-common/
COPY student-service/ student-service/
COPY grade-service/pom.xml grade-service/
COPY analytics-service/pom.xml analytics-service/
COPY api-gateway/pom.xml api-gateway/

# Build proto-common first, then package student-service
RUN mvn clean package -pl student-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN apk add --no-cache wget curl
COPY --from=builder /app/student-service/target/*.jar app.jar

EXPOSE 8081 9091
ENTRYPOINT ["java", "-jar", "app.jar"]
