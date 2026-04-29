FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :borrowing-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/borrowing-service/build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
