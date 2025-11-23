
#Portfolio – Spring Boot (Java 21) | AWS Elastic Beanstalk | CI/CD GitHub Actions
![Java](https://img.shields.io/badge/Java-21-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)
![GitHub Actions](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-black?logo=githubactions)
![AWS Elastic Beanstalk](https://img.shields.io/badge/AWS-Elastic%20Beanstalk-orange?logo=amazonaws)
![Docker](https://img.shields.io/badge/Docker-Optional-2496ED?logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow)


Ce projet est un portfolio personnel développé avec Spring Boot (Java 21), intégrant la génération de PDF via iTextPDF, et déployé automatiquement sur AWS Elastic Beanstalk grâce à une pipeline CI/CD GitHub Actions.

L'objectif est de présenter mes compétences techniques, notamment en développement backend, en architecture cloud moderne et en automatisation avec CI/CD.

```mermaid
flowchart TD

    A["Développement Local<br/>Spring Boot - Java 21"] --> B["Push sur GitHub<br/>Branch: main"]

    subgraph CI/CD - GitHub Actions
        B --> C["Build Maven<br/>mvn clean package"]
        C --> D["Tests Automatisés"]
        D --> E["Création du JAR<br/>target/app.jar"]
        E --> F["ZIP du Projet<br/>app.zip"]
        F --> G["Upload vers Amazon S3<br/>Bucket Elastic Beanstalk"]
        G --> H["Create Application Version<br/>Elastic Beanstalk"]
        H --> I["Update Environment<br/>Déploiement Automatique"]
    end

    I --> J["AWS Elastic Beanstalk<br/>Environnement Java / Docker"]
    J --> K["Application Portfolio<br/>Disponible en ligne"]

    K --> L["(Utilisateur Final)"]
```


##Fonctionnalités principales

- Développé avec Spring Boot 3 / Java 21
- Expositions d’API REST pour les sections du portfolio
- Génération de PDF (CV, documents, etc.) via iTextPDF
- Architecture propre et maintenable
- Déploiement automatisé via GitHub Actions
- Hébergement scalable sous AWS Elastic Beanstalk

##Technologies utilisées
###Backend

- Java 21
- Spring Boot 3
- iTextPDF
- Maven

###Cloud / DevOps
- AWS Elastic Beanstalk
- Amazon S3
- AWS CLI & EB CLI
- GitHub Actions

##Structure du projet
```mermaid
graph TD

    A[Projet Portfolio] --> B[src/main/java<br/>Code Spring Boot]
    A --> C[src/main/resources<br/>Configuration / Templates]
    A --> D[pom.xml<br/>Gestion des dépendances Maven]
    A --> E[Dockerfile<br/>Optionnel pour déploiement Docker]
    A --> F[.github/workflows/deploy.yml<br/>Pipeline CI/CD]
    A --> G[README.md<br/>Documentation du projet]
```

##Architecture Spring Boot interne
```mermaid
flowchart LR

    A[Controller<br/>Endpoints API] --> B[Service<br/>Logique Métier]
    B --> C[PDF Generator<br/>iTextPDF]
    B --> D[Repository<br/>Accès Données (optionnel)]
    A --> E[Models / DTO]
```

##Déploiement AWS Elastic Beanstalk
###Préparation AWS

- Créer une application Elastic Beanstalk
- Créer un environnement (Java ou Docker)
- Configurer un bucket S3 dédié
- Créer un utilisateur IAM CI/CD avec : AWSElasticBeanstalkFullAccess, AmazonS3FullAccess

```mermaid
| Secret                  | Description            |
| ----------------------- | ---------------------- |
| `AWS_ACCESS_KEY_ID`     | Clé IAM                |
| `AWS_SECRET_ACCESS_KEY` | Secret IAM             |
| `AWS_REGION`            | Exemple : `eu-west-3`  |
| `EB_APP_NAME`           | Nom Elastic Beanstalk  |
| `EB_ENV_NAME`           | Nom de l’environnement |
```
##Pipeline GitHub Actions (déploiement automatique)

```yaml
name: Deploy to AWS Elastic Beanstalk

on:
  push:
    branches: [ "main" ]

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Build with Maven
        run: mvn clean package -DskipTests

      - name: Install AWS CLI
        run: sudo apt-get install -y awscli zip

      - name: Create ZIP file for deployment
        run: zip -r app.zip .

      - name: Upload ZIP to S3
        run: |
          aws s3 cp app.zip s3://${{ secrets.EB_APP_NAME }}/app-v-${GITHUB_SHA}.zip

      - name: Deploy to Elastic Beanstalk
        run: |
          aws elasticbeanstalk create-application-version \
            --application-name "${{ secrets.EB_APP_NAME }}" \
            --version-label "v-${GITHUB_SHA}" \
            --source-bundle S3Bucket="${{ secrets.EB_APP_NAME }}",S3Key="app-v-${GITHUB_SHA}.zip"

          aws elasticbeanstalk update-environment \
            --environment-name "${{ secrets.EB_ENV_NAME }}" \
            --version-label "v-${GITHUB_SHA}"
```