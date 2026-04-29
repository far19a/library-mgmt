FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :reporting-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/reporting-service/build/libs/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
