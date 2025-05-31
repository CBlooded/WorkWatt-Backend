# WorkWatt-Backend

## Endpoints:

### Authentication:

### 1. `POST /api/v1/auth/authenticate`

Służy do uwierzytelniania istniejącego użytkownika (logowanie).

#### Request body

```json
{
  "email": "user@example.com",
  "password": "yourPassword123"
}
```

### 2. `POST /api/v1/auth/register`

Służy do rejestrowania nowego użytkownika.

#### Request body

```json
{
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": 0
}
```

### Account activation:

### 1. `POST /api/v1/user/password/set?h=...&n=...`

```json
{
  "hostId": "a1b2c3",
  "newPassword": "abc"
}
```

### 2. `GET /api/v1/user/password/host/validate?h=...&t=...`

```
    Params:
        - h: hostId [String] - id hosta aktywacji
        - t: tempPassword [String] - hasło tymczasowe 
```

## Database diagram:

