FROM openjdk:12-jdk-alpine
ARG JAR_FILER=target/*.jar
COPY ${JAR_FILE} wims.jar
ENTRYPOINT ["java", "-jar", "/wims.jar"]
EXPOSE 8080
