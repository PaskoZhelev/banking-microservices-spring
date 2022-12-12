#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

# Add the application's jar to the container
COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/config-server-0.0.1-SNAPSHOT.jar"]
