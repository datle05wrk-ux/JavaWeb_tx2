# Build stage
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy root pom and module sources
COPY pom.xml .
COPY proto-common/ proto-common/
COPY grade-service/ grade-service/
COPY student-service/pom.xml student-service/
COPY analytics-service/pom.xml analytics-service/
COPY api-gateway/pom.xml api-gateway/

# Build proto-common first, then package grade-service
RUN mvn clean package -pl grade-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN apk add --no-cache wget curl
COPY --from=builder /app/grade-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
