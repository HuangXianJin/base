FROM openjdk:8-alpine
RUN mkdir -p /app/ers

WORKDIR /app/ers

EXPOSE 8080

ADD ./target/base-application.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
