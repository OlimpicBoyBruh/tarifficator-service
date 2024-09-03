FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean -DskipTests package

FROM openjdk:17

COPY --from=build /app/target/profile-service-1.0-SNAPSHOT.jar /usr/local/profile-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 8085

CMD ["java", "-jar", "profile-service-1.0-SNAPSHOT.jar"]