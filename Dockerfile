FROM openjdk:11.0.16-jdk
VOLUME /tmp
EXPOSE 8761
ADD ./target/microservicio-dao-secrets-0.0.1-SNAPSHOT.jar secrets-app.jar
ENTRYPOINT ["java","-jar","secrets-app.jar"]