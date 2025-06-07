FROM openjdk:21-jdk-slim

WORKDIR /app

COPY analytical-agent-0.0.1-SNAPSHOT.jar /app/analytical-agent.jar

COPY /src/main/resources/answers/ /app/answers

VOLUME ["/app/answers"]

EXPOSE 9090

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "analytical-agent.jar"]