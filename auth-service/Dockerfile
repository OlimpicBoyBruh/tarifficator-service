FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package -DskipTests package

FROM openjdk:17

COPY --from=build /app/target/auth-service-1.0-SNAPSHOT.jar /usr/local/auth-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 8080

CMD ["java", "-jar", "auth-service-1.0-SNAPSHOT.jar"]