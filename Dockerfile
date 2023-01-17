FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/registration-api-0.0.1-SNAPSHOT.jar /app/person-address-api.jar

ENTRYPOINT ["java", "-jar", "person-address-api.jar"]