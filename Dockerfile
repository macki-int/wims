FROM openjdk:jdk-11.0.10_9-alpine
ADD target/springboot-docker-0.0.1-SNAPSHOT.jar .
EXPOSE 40000
CMD java -jar springboot-docker-0.0.1-SNAPSHOT.jar --envname=prod
