# Alpine Linux with OpenJDK JRE
FROM openjdk:11-jre
WORKDIR /app
# copy WAR into image
COPY target/operations-1.0-SNAPSHOT.war /app/operations.war
# run application with this command line
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "/app/operations.war"]