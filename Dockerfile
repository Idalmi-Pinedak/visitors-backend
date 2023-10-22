FROM openjdk:17.0.2
WORKDIR /app
COPY ./target/visitors-backend.jar /app
ENTRYPOINT ["java", "-jar","visitors-backend.jar"]

#FROM openjdk:17.0.2-jdk as build
#WORKDIR /app-code
#COPY .mvn/ .mvn
#COPY src ./src
#COPY mvnw pom.xml ./
#RUN ./mvnw clean install
#
#FROM openjdk:17.0.2 as pubish
#WORKDIR /app
#COPY --from=build /app-code/target/umg-clinica-ws.jar /app
#ENTRYPOINT ["java", "-jar","visitor-check-in-backend.jar"]
