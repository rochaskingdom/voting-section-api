FROM eclipse-temurin:17-jdk-alpine

VOLUME /main-app

ADD build/libs/voting-section-api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/app.jar"]