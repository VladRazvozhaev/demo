FROM openjdk:21-jre-slim

WORKDIR /app

COPY target/*.jar DemoApplication.jar

ENTRYPOINT ["java", "-jar", "DemoApplication.jar"]