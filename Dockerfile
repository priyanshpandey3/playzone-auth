FROM openjdk:8
ADD target/playzone-auth.jar playzone-auth.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/playzone-auth.jar"]