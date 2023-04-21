FROM amazoncorretto:17.0.6
#RUN yum install -y maven
#WORKDIR /usr/src/app
#COPY . .
#RUN mvn clean package

COPY target/account*.jar /usr/src/app/app.jar

ENTRYPOINT ["java", "-jar", "/usr/src/app/app.jar"]