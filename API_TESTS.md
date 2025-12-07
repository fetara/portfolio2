# 🧪 Tests API d'authentification

## Test avec cURL

### 1. Test de connexion réussie

```bash
curl -X POST http://localhost:8081/api/v1/account/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "arafet@test.com",
    "password": "password123"
  }'
```

**Résultat attendu** :
```json
{
  "id": 1,
  "firstname": "Arafet",
  "lastname": "Test",
  "email": "arafet@test.com",
  "message": "Authentification réussie"
}
```

### 2. Test avec mauvais mot de passe

```bash
curl -X POST http://localhost:8081/api/v1/account/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "arafet@test.com",
    "password": "wrongpassword"
  }'
```

**Résultat attendu** :
```json
{
  "message": "Email ou mot de passe incorrect"
}
```

### 3. Test avec email inexistant

```bash
curl -X POST http://localhost:8081/api/v1/account/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "unknown@test.com",
    "password": "password123"
  }'
```

**Résultat attendu** :
```json
{
  "message": "Email ou mot de passe incorrect"
}
```

### 4. Test avec le deuxième utilisateur

```bash
curl -X POST http://localhost:8081/api/v1/account/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@test.com",
    "password": "test123"
  }'
```

**Résultat attendu** :
```json
{
  "id": 2,
  "firstname": "John",
  "lastname": "Doe",
  "email": "john@test.com",
  "message": "Authentification réussie"
}
```

## Test avec Postman

### Configuration

1. **Méthode** : POST
2. **URL** : `http://localhost:8081/api/v1/account/login`
3. **Headers** :
   - `Content-Type: application/json`
4. **Body** (raw, JSON) :
```json
{
  "email": "arafet@test.com",
  "password": "password123"
}
```

### Collection Postman

Importez cette collection JSON dans Postman :

```json
{
  "info": {
    "name": "Portfolio Authentication API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Login Success",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"email\": \"arafet@test.com\",\n  \"password\": \"password123\"\n}"
        },
        "url": {
          "raw": "http://localhost:8081/api/v1/account/login",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8081",
          "path": ["api", "v1", "account", "login"]
        }
      }
    },
    {
      "name": "Login Failed - Wrong Password",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"email\": \"arafet@test.com\",\n  \"password\": \"wrongpassword\"\n}"
        },
        "url": {
          "raw": "http://localhost:8081/api/v1/account/login",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8081",
          "path": ["api", "v1", "account", "login"]
        }
      }
    },
    {
      "name": "Login User 2",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"email\": \"john@test.com\",\n  \"password\": \"test123\"\n}"
        },
        "url": {
          "raw": "http://localhost:8081/api/v1/account/login",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8081",
          "path": ["api", "v1", "account", "login"]
        }
      }
    }
  ]
}
```

## Test depuis le navigateur (Console développeur)

Ouvrez la console développeur (F12) et exécutez :

```javascript
// Test de connexion réussie
fetch('http://localhost:8081/api/v1/account/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    email: 'arafet@test.com',
    password: 'password123'
  })
})
.then(response => response.json())
.then(data => console.log('Succès:', data))
.catch(error => console.error('Erreur:', error));
```

## Vérifier la base de données H2

1. Accédez à la console H2 : `http://localhost:8081/h2-console`
2. Configuration :
   - **JDBC URL** : `jdbc:h2:mem:auth-service`
   - **Username** : `sa`
   - **Password** : (laisser vide)
3. Connectez-vous
4. Exécutez la requête SQL :
```sql
SELECT * FROM USERS;
```

## Tests automatisés (optionnel)

Pour créer des tests automatisés avec JUnit, ajoutez dans `AuthWithJWT/src/test/java` :

```java
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"arafet@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("arafet@test.com"))
                .andExpect(jsonPath("$.message").value("Authentification réussie"));
    }

    @Test
    void testLoginFailure() throws Exception {
        mockMvc.perform(post("/api/v1/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"arafet@test.com\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Email ou mot de passe incorrect"));
    }
}
```

## Résumé des codes HTTP

| Code | Signification | Cas d'usage |
|------|---------------|-------------|
| 200 OK | Succès | Authentification réussie |
| 401 Unauthorized | Non autorisé | Email/mot de passe incorrect |
| 500 Internal Server Error | Erreur serveur | Problème côté backend |

---

✅ **Conseil** : Commencez par tester avec cURL pour vérifier que l'API fonctionne avant de tester via l'interface web.
