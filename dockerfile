FROM openjdk:20-slim

WORKDIR /app

COPY target/javalin_project-1.0-SNAPSHOT.jar app.jar
COPY src/main/resources/database.db /app/src/main/resources/database.db

EXPOSE 7070

CMD ["java", "-jar", "app.jar"]