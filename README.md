
## Backend for Renti

### Development workflow

The project should be executed using a docker container, based on the provided `Dockerfile`. Also, as the project demands external dependecies, you should **run the project using**:
```
docker-compose up [-d] [--build]
```
(on the repo. root)

### Tests

Tests are disabled in the docker build, as they are meant to be executed externally on the pipeline. Therefore, if you want to **run the tests**, you should use: 
```
mvn test
```
or, if you are using docker for maven: 
```
docker run -it --rm maven mvn test
```

### API Docs

**Login**: POST to `/login` with *username* and *password* on request body. Authenticator object will be returned.

**Logout**: DELETE to `/login` with *User* on request body.