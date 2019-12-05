FROM openjdk:8
ADD target/docker-spring-boot.jar docker-spring-boot.war
EXPOSE 8081
