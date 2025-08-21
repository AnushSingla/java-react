FROM amazoncorretto:17-alpine3.18
EXPOSE 8080
COPY ./target/MyJavaApp-1.0.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
CMD [ "java","-jar","app.jar" ]