FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :book-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/book-service/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
