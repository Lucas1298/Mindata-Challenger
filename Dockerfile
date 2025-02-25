FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:21
COPY --from=build /usr/src/app/target/challenge-mindata-0.0.1.jar /usr/app/challenge-mindata-0.0.1.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/usr/app/challenge-mindata-0.0.1.jar"]