FROM openjdk:17-jdk-slim
COPY build/libs/Kitchen-Genius-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]