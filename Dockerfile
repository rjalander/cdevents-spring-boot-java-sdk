FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/cdevents-spring-boot-java-sdk-2.3.2.RELEASE.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]