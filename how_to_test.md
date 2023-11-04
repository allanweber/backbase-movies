# How to test the application

The endpoints are reachable at http://localhost:8080

* http://localhost:8080/health
* http://localhost:8080/v1/movies/best-picture/won?title=Avatar
* http://localhost:8080/v1/movies/ratings/rate?title=Avatar&rate=10
* http://localhost:8080/v1/movies/ratings/top-rated or http://localhost:8080/v1/movies/ratings/top-rated?limit=10

Complete documentation can be found at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API KEYS

* Valid OMDB API Keys for testing: **3307cf63** or **a0c16fd2**

## Seed Data

* The data will be loaded on application startup from a csv file located at **src/main/resources/data/academy_awards.csv** if you used the environment variable **SEED_ENABLED=true** or **moviesapi.seed.enabled=true** in the application.properties file.
* If you run using one of the shell scripts, describe in [how_to_run](how_to_run.md) file the seed will be enabled by default.

## Postman Collection

* If you love a good postman collection with tests and requests loops like me, you will find a collection in [./postman](./postman) folder.
* Just import it and run the requests as you like.
* There is one special request called **Rate a bunch of movies**, if you run it using the **Postman Runner** it will rate 15 movies with random rates from 1 to 10. You can trigger this runner as many times as you like. Then you will se the result in the **Get Top Rated Movies** request.