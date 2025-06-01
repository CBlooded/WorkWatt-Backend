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

### Computer:

### 1. `POST /api/v1/computer/addComputers`

```json
{
  "name": "Comp1",
  "consumption": 21.2,
  "amount": 0
}
```

### 2. `GET /api/v1/computer/getFreeComputers`

### 3. `POST /api/v1/computer/assignUser`

```json
{
  "userId": "aaa-bbb-ccc",
  "computerName": "Comp1"
}
```

### Usage Controller

### 1. `GET /api/v1/usage/history?s=...&e=...&u=...`

```
Params:
- s: String - czas startu w milisekundach (epoch millis)
- e: String - czas końca w milisekundach (epoch millis)
- u: String - userId (ID użytkownika)
```

### 2. `GET /api/v1/usage/supervisor/history?s=...&e=...&supervisor=...`
```
Params:
    - s: String - czas startu w milisekundach (epoch millis)
    - e: String - czas końca w milisekundach (epoch millis)
    - supervisor: String - ID supervisor'a
```

### 3. `POST /api/v1/usage/startWork`

### 4. `POST /api/v1/usage/endWork`

## Database diagram:

