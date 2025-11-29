FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8081
COPY target/POS-0.0.1-SNAPSHOT.jar POS.jar
ENTRYPOINT ["java","-jar","POS.jar"]