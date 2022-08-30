FROM openjdk:17-alpine
MAINTAINER Alexander Bonetskiy  <alexbonetskiy@gmail.com>

RUN apk --no-cache add curl

COPY ./entrypoint.sh /usr/bin/entrypoint.sh
COPY ./target/service-registry.jar /app/

ENTRYPOINT ["entrypoint.sh"]

CMD ["java", "-jar", "/app/service-registry.jar"]

EXPOSE 8761