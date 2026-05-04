FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar DemoApplication.jar

ENTRYPOINT ["java", "-jar", "DemoApplication.jar"]