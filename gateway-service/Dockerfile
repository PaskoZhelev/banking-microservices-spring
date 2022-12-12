#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

# Add the application's jar to the container
COPY target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/gateway-service-0.0.1-SNAPSHOT.jar"]
