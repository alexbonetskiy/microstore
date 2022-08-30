FROM openjdk:17-alpine
MAINTAINER Alexander Bonetskiy  <alexbonetskiy@gmail.com>

WORKDIR /app
COPY ./target/config-server.jar /app/
CMD ["java", "-jar", "/app/config-server.jar"]

EXPOSE 8888
