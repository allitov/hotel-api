# Hotel API
**API for hotel services.**

## Running locally
### Clone the repository and move to the working directory
```shell
git clone https://github.com/allitov/hotel-api.git
cd hotel-api
```

### Run the application
```shell
docker-compose --file ./docker/docker-compose.yaml up -d
```

#### Stop the application
```shell
docker-compose --project-name="hotel-api" down
```

### Run the application environment only
```shell
docker-compose --file ./docker/docker-compose-env.yaml up -d
```

#### Stop the application environment
```shell
docker-compose --project-name="hotel-api-env" down
```

## Documentation
To familiarize yourself with the application's API and see example queries,
you can refer to the [interactive Swagger documentation](http://localhost:8080/swagger-ui/index.html)
(available only after launching the application).