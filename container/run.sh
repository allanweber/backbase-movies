#!/bin/bash

mvn clean package

docker run -p 27017:27017 --name movies -d mongo
db_host=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' movies)
echo "db_host='${db_host}'"

docker build -t movies_api:latest -f container/Dockerfile .

docker run -p 8080:8080 --name movies_api -e MONGO_HOST="${db_host}" -e SEED_ENABLED=true movies_api:latest