# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/app

COPY target/*.jar ./organization-service.jar

ENTRYPOINT java -jar /opt/app/organization-service.jar
