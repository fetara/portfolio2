# Étape 1 : Build Maven avec Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler l'application en Java 21
RUN mvn clean package -DskipTests

# Étape 2 : Image finale en Java 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copier le JAR compilé
COPY --from=builder /app/target/*.jar portfolio.jar

# Exposer le port Spring Boot
EXPOSE 8080

# Démarrage de l'application
ENTRYPOINT ["java", "-jar", "/app/portfolio.jar"]
