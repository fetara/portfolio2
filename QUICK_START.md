# 🚀 Guide de Démarrage Rapide - Authentification Portfolio

## ⚡ Démarrage en 3 étapes

### Étape 1 : Démarrer le service Auth
```bash
cd Auth/AuthWithJWT
mvn clean install
mvn spring-boot:run
```

✅ Attendez le message : "Started AuthWithJwtApplication"

### Étape 2 : Démarrer l'application Profil
Dans un nouveau terminal :
```bash
cd Profil
mvn clean install
mvn spring-boot:run
```

✅ Attendez le message : "Started PortfolioApplication"

### Étape 3 : Tester la connexion
Ouvrez votre navigateur sur : `http://localhost:8080/login`

**Identifiants de test** :
- Email : `arafet@test.com`
- Mot de passe : `password123`

---

## 🎯 Checklist de vérification

### ✅ Services en cours d'exécution

- [ ] Service Auth accessible sur http://localhost:8081
- [ ] Application Profil accessible sur http://localhost:8080
- [ ] Console H2 accessible sur http://localhost:8081/h2-console

### ✅ Test de connexion

1. Ouvrir http://localhost:8080/login
2. Entrer : arafet@test.com / password123
3. Cliquer sur "Se connecter"
4. Vérifier la redirection vers /success
5. Voir le message "Hello Arafet"

### ✅ Test API direct

```bash
curl -X POST http://localhost:8081/api/v1/account/login \
  -H "Content-Type: application/json" \
  -d '{"email":"arafet@test.com","password":"password123"}'
```

**Résultat attendu** : 
```json
{"id":1,"firstname":"Arafet","lastname":"Test","email":"arafet@test.com","message":"Authentification réussie"}
```

---

## 🔧 Dépannage rapide

### Problème : Port déjà utilisé

**Service Auth (8081)** :
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8081 | xargs kill -9
```

**Application Profil (8080)** :
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Problème : Erreur de compilation Maven

```bash
# Nettoyer et recompiler
mvn clean install -U
```

### Problème : Base de données vide

Les utilisateurs sont créés automatiquement au démarrage. Vérifiez les logs :
```
✅ Utilisateur de test créé :
   Email: arafet@test.com
   Password: password123
```

Si ce message n'apparaît pas, vérifiez que `DataInitializer.java` est bien présent.

---

## 📊 Monitoring

### Logs en temps réel

**Service Auth** :
```bash
cd Auth/AuthWithJWT
mvn spring-boot:run | grep -E "Started|ERROR|Utilisateur"
```

**Application Profil** :
```bash
cd Profil
mvn spring-boot:run | grep -E "Started|ERROR"
```

### Vérifier les utilisateurs dans H2

1. Accéder à http://localhost:8081/h2-console
2. JDBC URL : `jdbc:h2:mem:auth-service`
3. Username : `sa` (password vide)
4. Requête SQL :
```sql
SELECT id, firstname, lastname, email FROM users;
```

---

## 🔄 Workflow de développement

### Modification du code

1. **Backend** : Arrêtez le service (Ctrl+C) et relancez-le
2. **Frontend (HTML/CSS/JS)** : Rafraîchir le navigateur (F5)

### Ajouter un nouvel utilisateur

Modifier `DataInitializer.java` :
```java
User newUser = User.builder()
    .firstname("Prénom")
    .lastname("Nom")
    .email("email@test.com")
    .password("motdepasse")
    .build();
userRepository.save(newUser);
```

### Changer les ports

**Auth** : Modifier `Auth/AuthWithJWT/src/main/resources/application.properties`
```properties
server.port=8081  # Changer ce numéro
```

**Profil** : Modifier `Profil/src/main/resources/application.properties`
```properties
server.port=8080  # Changer ce numéro
```

⚠️ **Important** : Si vous changez le port Auth, modifiez aussi l'URL dans `login.html` :
```javascript
const AUTH_API_URL = 'http://localhost:NOUVEAU_PORT/api/v1/account/login';
```

---

## 📱 Tester sur mobile (même réseau local)

1. Trouver votre IP locale :
   - Windows : `ipconfig` → Chercher "IPv4"
   - Linux/Mac : `ifconfig` → Chercher "inet"

2. Modifier `login.html` :
```javascript
const AUTH_API_URL = 'http://VOTRE_IP:8081/api/v1/account/login';
```

3. Accéder depuis mobile :
```
http://VOTRE_IP:8080/login
```

---

## 🏗️ Structure des URLs

| URL | Description | Port |
|-----|-------------|------|
| `/login` | Page de connexion | 8080 |
| `/success` | Page après authentification | 8080 |
| `/home` | Page d'accueil portfolio | 8080 |
| `/api/v1/account/login` | API d'authentification | 8081 |
| `/h2-console` | Console base de données | 8081 |

---

## 💡 Commandes utiles

### Compilation sans tests
```bash
mvn clean install -DskipTests
```

### Lancer en mode debug
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Créer un JAR exécutable
```bash
mvn clean package
java -jar target/AuthWithJWT-0.0.1-SNAPSHOT.jar
```

---

## 🎓 Comprendre le flux d'authentification

```
┌─────────────┐       1. GET /login      ┌─────────────┐
│  Navigateur │ ───────────────────────> │   Profil    │
│             │                           │  (8080)     │
└─────────────┘                           └─────────────┘
      │
      │ 2. Saisie email/password
      │
      ▼
┌─────────────┐   3. POST login data     ┌─────────────┐
│  login.html │ ───────────────────────> │    Auth     │
│             │                           │  (8081)     │
│             │ <───────────────────────  │             │
└─────────────┘   4. User data (JSON)    └─────────────┘
      │
      │ 5. Stockage localStorage
      │ 6. Redirection /success
      ▼
┌─────────────┐
│ success.html│  Affiche "Hello Prénom"
└─────────────┘
```

---

## 🎉 Félicitations !

Si tous les tests passent, votre système d'authentification est opérationnel ! 

**Prochaines étapes** :
- Consulter AUTHENTICATION_README.md pour plus de détails
- Consulter API_TESTS.md pour tester l'API
- Personnaliser le design des pages
- Ajouter de nouvelles fonctionnalités

---

**Besoin d'aide ?** Consultez les logs dans les consoles des deux services.
