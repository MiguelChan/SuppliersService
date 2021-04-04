FROM openjdk:16-jdk-buster

VOLUME /tmp

COPY ./build/libs/SuppliersService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app.jar"]