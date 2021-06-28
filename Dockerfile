FROM openjdk:16-jdk-buster

VOLUME /tmp

COPY ./build/libs/SuppliersService-1.0.0.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app.jar"]