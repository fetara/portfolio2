# Étape 1 : Build Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier le pom et les sources
COPY Profil/pom.xml ./pom.xml
RUN mvn dependency:go-offline
COPY src ./src

# Build
RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le JAR
COPY --from=builder /app/target/*.jar portfolio.jar

# Port de l'app
EXPOSE 5000

CMD ["java", "-jar", "portfolio.jar"]

