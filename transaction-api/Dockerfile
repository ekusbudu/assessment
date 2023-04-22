#FROM maven:3.8.3-openjdk-17-slim AS builder
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src ./src
#RUN mvn package -DskipTests

FROM amazoncorretto:17.0.6

COPY target/transaction*.jar /usr/src/app/app.jar

ENTRYPOINT ["java", "-jar", "/usr/src/app/app.jar"]