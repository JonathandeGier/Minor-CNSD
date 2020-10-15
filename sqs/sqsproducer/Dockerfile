FROM openjdk:11-slim
COPY target/sqsproducer-0.0.1-SNAPSHOT.jar /usr/app/producer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=aws", "-jar", "/usr/app/producer.jar"]