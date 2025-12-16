# Étape 1 : Build Maven avec Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier les fichiers pom.xml
COPY Profil/pom.xml ./Profil/pom.xml
COPY Auth/AuthWithJWT/pom.xml ./Auth/AuthWithJWT/pom.xml

# Copier le code source
COPY Profil/src ./Profil/src
COPY Auth/AuthWithJWT/src ./Auth/AuthWithJWT/src

# Compiler les applications
RUN mvn -f Profil/pom.xml clean package -DskipTests
RUN mvn -f Auth/pom.xml clean package -DskipTests

# Étape 2 : Image finale en Java 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copier les JARs compilés
COPY --from=builder /app/Profil/target/*.jar portfolio.jar
COPY --from=builder /app/Auth/target/*.jar auth.jar

# Exposer les ports
EXPOSE 5000 5001

# Démarrer les deux applications
CMD ["sh", "-c", "java -jar /app/portfolio.jar & java -jar /app/auth.jar"]
