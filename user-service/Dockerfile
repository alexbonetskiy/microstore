FROM openjdk:17-alpine
MAINTAINER Alexander Bonetskiy  <alexbonetskiy@gmail.com>

RUN apk --no-cache add curl

COPY ./entrypoint.sh /usr/bin/entrypoint.sh
COPY ./target/user-service.jar /app/

ENTRYPOINT ["entrypoint.sh"]

CMD ["java", "-jar", "/app/user-service.jar"]

EXPOSE 8003