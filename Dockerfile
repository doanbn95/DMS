FROM openjdk:8
ADD target/DMS.jar DMS.war
EXPOSE 8081
