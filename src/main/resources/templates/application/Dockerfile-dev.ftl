# ===============================
# DEV Dockerfile
# ===============================
FROM eclipse-temurin:21-jdk AS dev

WORKDIR /app

# Copy source and Maven wrapper for iterative builds
COPY . /app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java","-jar","target/${serviceName}-service-1.0-SNAPSHOT.jar"]
