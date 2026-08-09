# FitSync-A Fitness Monitoring Platform

A Spring Boot REST API for tracking workouts, weight entries, and BMI, with
JWT-based authentication.

## Tech stack

- Java 21, Spring Boot 4
- Spring Web, Spring Data JPA, Spring Security, Bean Validation
- MySQL
- JJWT (JSON Web Tokens)

## Getting started

1. Create a MySQL database named `microfit`.
2. Set environment variables (or edit the defaults in
   `src/main/resources/application.properties`):

   | Variable            | Purpose                              | Default (dev only) |
   |----------------------|---------------------------------------|---------------------|
   | `DB_URL`             | JDBC URL                              | `jdbc:mysql://localhost:3306/microfit` |
   | `DB_USERNAME`        | DB username                           | `root` |
   | `DB_PASSWORD`        | DB password                           | `1234` |
   | `JWT_SECRET`         | HMAC signing key for JWTs             | a placeholder — **override this in production** |
   | `JWT_EXPIRATION_MS`  | Token lifetime in ms                  | `86400000` (24h) |

3. Run it:

   ```bash
   mvn spring-boot:run
   ```

   The API starts on `http://localhost:8080`.

## Authentication

Almost every endpoint (except `/api/auth/**` and `/api/bmi/**`) requires a
JWT. Get one by registering or logging in, then send it as:

```
Authorization: Bearer <token>
```

### `POST /api/auth/register`
```json
{
  "firstName": "Gagan",
  "lastName": "K",
  "email": "gagan@example.com",
  "password": "secret123",
  "age": 22,
  "height": 175,
  "weight": 70
}
```
Returns `201 Created` with a token.

### `POST /api/auth/login`
```json
{ "email": "gagan@example.com", "password": "secret123" }
```
Returns `200 OK` with a token, or `401 Unauthorized` on bad credentials.

## Endpoints

| Method | Path                        | Auth required | Notes |
|--------|------------------------------|----------------|-------|
| POST   | `/api/auth/register`         | No             | Creates a user, returns a JWT |
| POST   | `/api/auth/login`             | No             | Returns a JWT |
| POST   | `/api/bmi/calculate`          | No             | Stateless BMI calculation |
| GET    | `/api/users/me`               | Yes            | Current user's profile |
| GET    | `/api/users`                  | Yes            | All users |
| GET    | `/api/users/{id}`             | Yes            | One user |
| PUT    | `/api/users/{id}`             | Yes            | Update a user (password optional) |
| DELETE | `/api/users/{id}`             | Yes            | Delete a user |
| POST   | `/api/workouts`               | Yes            | Add a workout for the logged-in user |
| GET    | `/api/workouts/me`            | Yes            | Logged-in user's workouts |
| GET    | `/api/workouts`               | Yes            | All workouts |
| GET    | `/api/workouts/{id}`          | Yes            | One workout |
| PUT    | `/api/workouts/{id}`          | Yes            | Update a workout |
| DELETE | `/api/workouts/{id}`          | Yes            | Delete a workout |
| GET    | `/api/dashboard`               | Yes            | Total workouts / calories / avg duration for the logged-in user |
| POST   | `/api/weights`                 | Yes            | Add a weight entry for the logged-in user |
| GET    | `/api/weights/me`               | Yes            | Logged-in user's weight entries |

## Frontend
HTML,CSS,JavaScript

**Note:** there's no registration page in this UI — accounts are created by
calling `POST /api/auth/register` directly (Postman, curl, or Swagger once
that's added). Worth adding a register form if you want a fully click-through
demo.



## Possible next steps

- Role-based access control (e.g. an admin role that can see all users'
  data, vs. regular users limited to their own).
- Refresh tokens.
- Rate limiting on `/api/auth/login`.
