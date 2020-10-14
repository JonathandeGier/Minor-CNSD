FROM openjdk:11-slim
COPY target/sqsconsumer-0.0.1-SNAPSHOT.jar /usr/app/consumer.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/consumer.jar"]