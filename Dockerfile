# Étape 1 : Build Maven avec Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY Auth/pom.xml ./Auth
COPY Profil/pom.xml ./Profil

#RUN mvn dependency:go-offline
RUN mvn -f Auth/pom.xml clean package -DskipTests
RUN mvn -f Profil/pom.xml clean package -DskipTests

# Copier le code source
COPY src ./src

# Compiler l'application en Java 21
#RUN mvn clean package -DskipTests

# Étape 2 : Image finale en Java 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copier le JAR compilé
COPY --from=builder /app/Profil/target/*.jar portfolio.jar
COPY --from=builder /app/Auth/target/*.jar Auth.jar


# Exposer le port Spring Boot
EXPOSE 5000
EXPOSE 8081

# Démarrage de l'application
#ENTRYPOINT ["java", "-jar", "/app/portfolio.jar"]
CMD ["sh", "-c", "java -jar /app/portfolio.jar & java -jar /app/Auth.jar"]
