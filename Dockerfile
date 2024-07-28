# Stage 1: Build the Maven project
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn package -DskipTests

FROM ubuntu:24.04
EXPOSE 8080

RUN apt-get update && \
    apt-get install -y unzip curl wget

RUN mkdir /opt/amazon-corretto-17-x64-linux-jdk

RUN wget https://corretto.aws/downloads/latest/amazon-corretto-17-x64-linux-jdk.tar.gz && \
    tar -zcvf amazon-corretto-17-x64-linux-jdk.tar.gz /opt/amazon-corretto-17-x64-linux-jdk

#Setup Java Home
ENV JAVA_HOME=/opt/amazon-corretto-21-x64-linux-jdk
RUN export JAVA_HOME
ENV PATH=$PATH:${JAVA_HOME}/bin
RUN export PATH

WORKDIR /app
COPY --from=build /app/target/*.jar my-project.jar

CMD ["java", "-jar", "my-project.jar"]