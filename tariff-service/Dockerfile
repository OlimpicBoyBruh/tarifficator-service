FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean -DskipTests package

FROM openjdk:17

COPY --from=build /app/target/tariff-service-1.0-SNAPSHOT.jar /usr/local/tariff-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 8095

CMD ["java", "-jar", "tariff-service-1.0-SNAPSHOT.jar"]