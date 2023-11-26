# Use the quay.io/quarkus/ubi-quarkus-mandrel:21.3-java21 builder image
FROM quay.io/quarkus/ubi-quarkus-mandrel:21.3-java21

# Set the working directory in the container
WORKDIR /work/application

# Copy the application JAR file and configuration files into the container
COPY target/*-runner.jar /work/application/application.jar
COPY src/main/resources/application.yaml /work/application/application.yaml

# Expose the HTTP port
EXPOSE 8080

# Specify the command to run on container startup
CMD ["./application.jar"]