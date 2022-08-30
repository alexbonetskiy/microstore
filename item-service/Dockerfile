FROM openjdk:17-alpine
MAINTAINER Alexander Bonetskiy  <alexbonetskiy@gmail.com>

RUN apk --no-cache add curl

COPY ./target/item-service.jar /app/
COPY ./entrypoint.sh /usr/bin/entrypoint.sh

ENTRYPOINT ["entrypoint.sh"]

CMD ["java", "-jar", "/app/item-service.jar"]

EXPOSE 8001