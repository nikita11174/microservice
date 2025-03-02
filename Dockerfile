FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/microservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} microservice.jar
ENTRYPOINT ["java", "-jar", "microservice.jar"]
