FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -q -DskipTests package

FROM jetty:12.0-jre17
COPY --from=build /workspace/target/api_conjunto-1.0-SNAPSHOT.war /var/lib/jetty/webapps/ROOT.war
EXPOSE 8080
ENV JAVA_OPTS="-Djetty.http.host=0.0.0.0 -Djetty.http.port=8080"