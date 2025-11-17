FROM amazoncorretto:17
LABEL authors="leonardogloria"
WORKDIR /app
COPY app.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]