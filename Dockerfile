FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/Permit_Locator-0.0.1-SNAPSHOT.jar Permit_Locator-0.0.1-SNAPSHOT.jar
EXPOSE 8080

CMD ["java", "-jar", "Permit_Locator-0.0.1-SNAPSHOT.jar"]
LABEL authors="nicandrade"
