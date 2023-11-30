FROM openjdk:11

# Install curl and git
RUN apt-get update && apt-get install -y curl git

# Update git to fix CVE-2022-41903
RUN apt-get upgrade -y git

COPY *.jar app.jar

ENV EUREKA_CLIENT_SERVICE_URL=http://localhost:8761/eureka
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://10.0.5.231:5432/correspondence
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres
ENV APP_MIGRATION=true
ENV APP_REINSTALL=false
ENV APP_REPAIR=false
ENV APP_HOSTNAME=http://eosc-dev.siseth.com/

ENTRYPOINT ["java", "-jar", "/app.jar", \
           "--spring.datasource.url=${SPRING_DATASOURCE_URL}"]