FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
COPY target/demo-restapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]