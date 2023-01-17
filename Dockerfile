#
# Build stage
#
FROM openjdk
WORKDIR /app
COPY target/registration-api-0.0.1-SNAPSHOT.jar /app/registration-api.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/registration-api.jar"]

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/registration-api-0.0.1-SNAPSHOT.jar /usr/local/lib/registration-api.jar
EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","/usr/local/lib/registration-api.jar"]