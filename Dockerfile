# Etapa 1: Construcción
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM tomcat:latest
WORKDIR /app
# Eliminar las aplicaciones predeterminadas de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
# Copiar el WAR generado en la etapa de construcción al directorio webapps de Tomcat
COPY --from=build /app/target/DISHPLANET-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080



