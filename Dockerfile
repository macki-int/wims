FROM openjdk:12-jdk-alpine

WORKDIR /opt

COPY target/wims-docker.jar wims.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "wims.jar"]