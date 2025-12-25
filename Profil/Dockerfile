# Étape 1 : Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le JAR (déjà build par la pipeline CI/CD)
COPY portfolio.jar .

# Port de l'app
EXPOSE 5000

CMD ["java", "-jar", "portfolio.jar"]

