FROM alpine/java:21-jdk

# Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
COPY target/*.jar /Ticket4Ticket.jar

# Izlaganje porta
EXPOSE 8080

# Naredba kojom se pokrece aplikacija
ENTRYPOINT ["java","-jar","/Ticket4Ticket.jar"]