FROM openjdk:jdk-11.0.10_9-alpine
ADD target/wims-0.0.1-SNAPSHOT.jar .
EXPOSE 40000
CMD java -jar wims-0.0.1-SNAPSHOT.jar --envname=prod
