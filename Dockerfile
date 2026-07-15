FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -q -DskipTests package

FROM tomcat:10.1-jre17-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /workspace/target/api_conjunto-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
ENV CATALINA_OPTS="-Djava.awt.headless=true"
EXPOSE 8080
ENV PORT=8080