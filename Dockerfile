FROM openjdk:8
ADD target/DMS.war DMS.war
EXPOSE 8081
