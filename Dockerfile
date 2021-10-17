FROM openjdk:12-jdk-alpine

#ARG JAR_FILER=target/*.jar
#COPY ${JAR_FILE} wims.jar
COPY target/wims.jar wims.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "wims.jar"]

#CMD ["java", "-jar", "/wims.jar"]
