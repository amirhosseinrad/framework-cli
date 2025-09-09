# ===============================
# PROD Dockerfile (multi-stage)
# ===============================

# --- Build Stage ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace/app
COPY . .
RUN ./mvnw clean package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /workspace/app/target/${serviceName}-service-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
