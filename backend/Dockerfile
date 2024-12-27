#FROM alpine/java:21-jdk
#
## Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
#COPY target/*.jar /Ticket4Ticket.jar
#
## Izlaganje porta
#EXPOSE 8080
#
## Naredba kojom se pokrece aplikacija
#ENTRYPOINT ["java","-jar","/Ticket4Ticket.jar"]


# Use the official Maven image as the base image
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and any other necessary configuration files
COPY ./pom.xml /app
COPY ./src /app/src

# Build the application
RUN mvn clean package -Dmaven.test.skip=true

# Create a new image for running the application
FROM alpine/java:21-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]