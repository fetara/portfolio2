# 🔐 Système d'Authentification - Portfolio

## 📋 Description

Ce projet comprend un système d'authentification complet avec deux applications Spring Boot :

1. **Auth (AuthWithJWT)** - Service d'authentification (port 8081)
2. **Profil** - Application portfolio avec pages de connexion (port 8080)

## 🚀 Démarrage

### Prérequis
- Java 17 ou supérieur
- Maven
- Un navigateur web moderne

### 1. Démarrer le service d'authentification (Auth)

```bash
cd Auth/AuthWithJWT
mvn spring-boot:run
```

Le service sera accessible sur : `http://localhost:8081`

### 2. Démarrer l'application Portfolio (Profil)

Dans un nouveau terminal :

```bash
cd Profil
mvn spring-boot:run
```

L'application sera accessible sur : `http://localhost:8080`

## 🔑 Comptes de test

Deux utilisateurs sont automatiquement créés au démarrage :

### Utilisateur 1
- **Email** : `arafet@test.com`
- **Mot de passe** : `password123`

### Utilisateur 2
- **Email** : `john@test.com`
- **Mot de passe** : `test123`

## 📱 Utilisation

### Accéder à la page de connexion

Ouvrez votre navigateur et allez sur :
```
http://localhost:8080/login
```

### Processus de connexion

1. Entrez votre email et mot de passe
2. Cliquez sur "Se connecter"
3. Si l'authentification réussit, vous serez redirigé vers la page de succès
4. La page affiche un message "Hello [Prénom]" personnalisé

### Pages disponibles

- `/login` - Page de connexion
- `/success` - Page affichée après authentification réussie
- `/home` - Page d'accueil du portfolio (existante)

## 🏗️ Architecture

### Structure des fichiers créés

```
Profil/
├── src/main/resources/templates/
│   ├── login.html          # Page de connexion
│   └── success.html        # Page de succès
└── src/main/java/com/application/portfolio/controller/
    └── LoginController.java

Auth/AuthWithJWT/
├── src/main/java/com/portfolio/auth/
│   ├── web/
│   │   └── AccountController.java
│   ├── services/
│   │   ├── IAccountService.java
│   │   └── AccountService.java
│   ├── repositories/
│   │   └── UserRepository.java
│   ├── dtos/
│   │   ├── LoginRequestDto.java
│   │   └── LoginResponseDto.java
│   ├── entites/
│   │   └── User.java
│   └── config/
│       └── DataInitializer.java
```

### API d'authentification

**Endpoint** : `POST http://localhost:8081/api/v1/account/login`

**Corps de la requête** :
```json
{
  "email": "arafet@test.com",
  "password": "password123"
}
```

**Réponse en cas de succès** (200 OK) :
```json
{
  "id": 1,
  "firstname": "Arafet",
  "lastname": "Test",
  "email": "arafet@test.com",
  "message": "Authentification réussie"
}
```

**Réponse en cas d'échec** (401 Unauthorized) :
```json
{
  "message": "Email ou mot de passe incorrect"
}
```

## 🎨 Fonctionnalités de l'interface

### Page de connexion (`login.html`)
- Design moderne et responsive
- Validation des champs en temps réel
- Indicateur de chargement pendant l'authentification
- Affichage des messages d'erreur
- Toggle pour afficher/masquer le mot de passe
- Animations fluides

### Page de succès (`success.html`)
- Message de bienvenue personnalisé
- Affichage des informations utilisateur
- Statistiques (projets, favoris, messages)
- Boutons d'action (Accueil, Portfolio, Déconnexion)
- Session persistante avec localStorage
- Animations d'entrée

## 🔒 Sécurité

⚠️ **Note importante** : Cette implémentation est à des fins de démonstration.

Pour une utilisation en production, il faudrait :
- Encoder les mots de passe avec BCrypt
- Implémenter JWT (JSON Web Tokens)
- Ajouter HTTPS
- Configurer CORS correctement
- Ajouter une gestion de sessions côté serveur
- Implémenter un système de refresh tokens

## 🛠️ Technologies utilisées

### Backend
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- Lombok

### Frontend
- HTML5
- CSS3 (avec animations)
- JavaScript (Vanilla)
- Fetch API

## 🐛 Dépannage

### Le service Auth ne démarre pas
- Vérifiez que le port 8081 est libre
- Vérifiez que Java 17+ est installé : `java -version`

### L'application Profil ne démarre pas
- Vérifiez que le port 8080 est libre
- Assurez-vous que le service Auth est démarré en premier

### Erreur de connexion
- Vérifiez que les deux services sont démarrés
- Vérifiez que vous utilisez un des comptes de test fournis
- Consultez les logs dans les consoles pour plus de détails

### Erreur CORS
- Vérifiez que l'annotation `@CrossOrigin` est présente dans AccountController
- Vérifiez que l'URL de l'API dans login.html correspond à votre configuration

## 📝 Améliorations futures

- [ ] Ajouter l'inscription d'utilisateurs
- [ ] Implémenter JWT pour les sessions
- [ ] Ajouter la récupération de mot de passe
- [ ] Implémenter un système de rôles
- [ ] Ajouter des tests unitaires
- [ ] Créer une page de profil utilisateur
- [ ] Ajouter la gestion d'avatar

## 📧 Contact

Pour toute question ou suggestion, n'hésitez pas à me contacter !

---

Fait avec ❤️ par Arafet
