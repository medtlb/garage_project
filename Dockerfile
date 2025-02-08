FROM openjdk:19-slim

WORKDIR /app

COPY build/app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]