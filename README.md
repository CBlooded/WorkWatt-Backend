# WorkWatt-Backend

WorkWatt-Backend to aplikacja serwerowa zbudowana w technologii Java Spring Boot. Obsługuje rejestrację użytkowników, uwierzytelnianie, zarządzanie hasłami i tokenami aktywacyjnymi.

## 🔧 Technologie

- Java 17
- Spring Boot
- Spring Security
- Maven
- JWT (JSON Web Token)
- REST API

---

## 📌 Endpointy

### 🔐 Autoryzacja i rejestracja

#### `POST /api/v1/auth/authenticate`
Uwierzytelnia istniejącego użytkownika.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "yourPassword123"
}
```

**Response:**
```json
{
  "accessToken": "jwt_token_here",
  "refreshToken": "refresh_token_here"
}
```

---

#### `POST /api/v1/auth/register`
Rejestruje nowego użytkownika.

**Request Body:**
```json
{
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": 0
}
```

---

### 🔑 Resetowanie hasła i weryfikacja

#### `POST /api/v1/user/password/set?h=...&n=...`
Ustawia nowe hasło dla konta użytkownika.

**Query Params:**
- `h` – identyfikator hosta
- `n` – token lub inny parametr bezpieczeństwa

**Request Body:**
```json
{
  "hostId": "a1b2c3",
  "newPassword": "securePassword123"
}
```

---

#### `GET /api/v1/user/password/host/validate?h=...&t=...`
Sprawdza ważność tokena resetowania hasła.

**Query Params:**
- `h` – hostId
- `t` – token

---

## ▶️ Uruchomienie lokalne

1. Sklonuj repozytorium:
```bash
git clone https://github.com/CBlooded/WorkWatt-Backend.git
cd WorkWatt-Backend
```

2. Zbuduj projekt:
```bash
./mvnw clean install
```

3. Uruchom aplikację:
```bash
./mvnw spring-boot:run
```

Aplikacja domyślnie będzie dostępna pod adresem `http://localhost:8080`.

---

## 📁 Struktura projektu (skrótowa)

```
src/
├── main/
│   ├── java/
│   │   └── com/workwatt/
│   │       ├── auth/
│   │       ├── config/
│   │       ├── user/
│   │       └── ...
│   └── resources/
│       ├── application.properties
│       └── ...
```

---

## 👤 Autor

Projekt stworzony przez [CBlooded](https://github.com/CBlooded).

---

