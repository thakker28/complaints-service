FROM openjdk:11
VOLUME /tmp
EXPOSE 9095
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]