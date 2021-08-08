FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD netease-music-copy-1.2.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]