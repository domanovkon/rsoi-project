FROM openjdk:11
ADD target/spring-boot-docker-gateway.jar spring-boot-docker-gateway.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker-gateway.jar"]
EXPOSE 8080