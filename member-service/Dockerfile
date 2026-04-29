FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :member-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/member-service/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
