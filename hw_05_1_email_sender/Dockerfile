FROM maven as build
WORKDIR /profitsoft
COPY pom.xml .
COPY src ./src
RUN mvn -Dmaven.test.skip=true clean package

FROM openjdk:17-alpine

#creating new directory inside container
WORKDIR /profitsoft

#coping jar file from previous stage
COPY --from=build /profitsoft/target/*.jar app.jar

#choosing command to start an app
ENTRYPOINT ["java", "-jar", "app.jar"]
