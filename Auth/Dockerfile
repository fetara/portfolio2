# Étape 1 : Build Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier le pom et les sources
COPY pom.xml .
COPY src ./src

# Build
RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le JAR
COPY --from=builder /app/target/*.jar auth.jar

# Port de l'app
EXPOSE 5000

CMD ["java", "-jar", "auth.jar"]

