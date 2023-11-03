FROM openjdk:17-alpine

EXPOSE 8080

ARG Xmx="-Xmx1500m"
ARG Xms="-Xmx1500m"

ENV JAVA_OPTS "$Xmx \
              $Xms \
              -XX:+UseParallelGC"

ADD target/movies_api*.jar movies_api.jar

CMD [ "sh", "-c", "java $JAVA_OPTS -jar movies_api.jar" ]