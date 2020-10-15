FROM openjdk:11-slim
COPY target/sqsconsumer-0.0.1-SNAPSHOT.jar /usr/app/consumer.jar
RUN apt-get install -y openssh-server
EXPOSE 8081
EXPOSE 22
ENTRYPOINT ["java","-jar","/usr/app/consumer.jar"]