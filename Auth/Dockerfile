# Étape 1 : Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le JAR (déjà build par la pipeline CI/CD)
COPY auth.jar .

# Port de l'app
EXPOSE 5000

CMD ["java", "-jar", "auth.jar"]

