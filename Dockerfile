FROM maven AS build

ARG POSTGRES_HOST
ARG POSTGRES_PORT
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD
ENV POSTGRES_HOST=${POSTGRES_HOST}
ENV POSTGRES_PORT=${POSTGRES_PORT}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

COPY marketplace/pom.xml /usr/src/app/
# Download the package and make it cached in docker image
RUN mvn -B -f /usr/src/app/pom.xml dependency:go-offline
COPY marketplace/src/ /usr/src/app/src/
RUN mvn -f /usr/src/app/pom.xml package -Dmaven.skip.tests=true

FROM openjdk:9
RUN echo "Running"
RUN echo $POSTGRES_HOST
COPY --from=build /usr/src/app/target/marketplace-0.0.1-SNAPSHOT.jar /usr/app/marketplace-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/marketplace-0.0.1-SNAPSHOT.jar"]

