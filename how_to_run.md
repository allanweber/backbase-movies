# How to run the application

## Quick start (preferred way)

* **Quickly run the application with docker compose**: `./container/run-compose.sh`

## Dependencies

* Java 17
* Maven 3+
* Docker
* Mongo: `docker run -d -p 27017:27017 --name movies -d mongo`

## Environment Variables
* MONGO_HOST: host name, and port if applicable (other than default localhost:27017)
* SEED_ENABLED: if true, will seed the database with the data from the csv file on startup

## Options to run the application

I am giving you many options to run the application, choose the one that fits you better.

### Using maven and java jar

* You can build the application and run the jar file: `mvn clean package && java -jar target/movies_api-1.0.0.jar` or
`mvn clean package && java -jar target/movies_api-1.0.0.jar --SEED_ENABLED=true`

### Using docker

Here you also have many options, you can build the image and run it, the shell scripts I prepare for you:

* `./container/run.sh`: will build the package, create a docker container, build the application image and run it.
* `./container/run-compose.sh`: will use docker compose to build the application image and dependencies and run it.
* `./container/clean.sh`: will remove the docker containers and images to clean up your environment. **Will remove the
  mongo container as well, so be careful.**