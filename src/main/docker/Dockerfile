FROM openjdk:8-jdk-alpine
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 6100
ADD /libs/${name}-${version}.jar /app.jar
