
```bash
docker run -p 27017:27017 --name movies -d mongo
docker inspect --format '{{ .NetworkSettings.IPAddress }}' movies
```

```bash

mvn clean package
docker build -t movies_api:latest -f container/Dockerfile .


docker run -p 8080:8080 --name movies_api -e MONGO_HOST="172.17.0.2" movies_api:latest
docker run -p 8080:8080 --name movies_api -e MONGO_HOST="172.17.0.2" -e SEED_ENABLED=true movies_api:latest
```

OMDB key 3307cf63 or a0c16fd2

Notes
I did not use a postgres enum, because we are now only working with Best Pictures, if we need to add a new enum
value we need to drop the existing one and create a anew one, what is quite a fuzz, so I decided to use a varchar, and
use enum values in the application layer.

I skip reading data from award that are not liked to specific movies


<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.10.0</version>
</dependency>

MONGO

<dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>8.0.0.Final</version>
        </dependency>

<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-pmd-plugin</artifactId>

