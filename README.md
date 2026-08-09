# MicroFit — Fitness Tracking API

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
   ./mvnw spring-boot:run
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

The `MicroFrontend/` folder is a plain HTML/CSS/JS UI (Bootstrap + Chart.js)
— `login.html` → `dashboard.html`. Open `login.html` directly in a browser
(or serve the folder with any static server) while the backend is running
on `http://localhost:8080`.

It talks to the API defined in `app.js`/`login.js` via `API_BASE`, currently
set to `http://localhost:8080` — change that constant if you deploy the
backend elsewhere.

**Note:** there's no registration page in this UI — accounts are created by
calling `POST /api/auth/register` directly (Postman, curl, or Swagger once
that's added). Worth adding a register form if you want a fully click-through
demo.

## What changed from the original version

- **Real authentication.** Registration now saves users with BCrypt-hashed
  passwords; login checks real credentials against the database instead of
  one hardcoded account.
- **The JWT filter actually authenticates requests now.** Before, tokens
  were validated but never used to populate Spring Security's context, and
  every endpoint was `permitAll()` regardless. Protected endpoints now
  genuinely require a valid token.
- **Passwords never leak in API responses** (`@JsonProperty(WRITE_ONLY)`).
- **Consistent error responses.** A `GlobalExceptionHandler` returns proper
  `404`/`409`/`401`/`400` JSON errors instead of a silent `null` body.
- **Input validation** on all request bodies (email format, required fields,
  positive numbers, minimum password length).
- **Weight entries and workouts are linked to the user who created them**,
  derived from the JWT rather than trusted from the request body.
- **Secrets externalized** to environment variables instead of hardcoded in
  source.
- Removed a duplicate, unused `LoginRequest` class.
- Fixed a null-pointer risk in the dashboard's calorie/duration aggregation.
- Date fields (`workoutDate`, `entryDate`) are now `LocalDate` instead of
  free-form strings.
- **`MicroFrontend/login.js` and `app.js` fixed to match the new backend
  contract**: login now parses the JSON `{token, ...}` response instead of
  expecting raw text, and every dashboard/workout/weight request now sends
  `Authorization: Bearer <token>` and hits the user-scoped `/me` endpoints
  (previously it called the API with no auth header at all, and the login
  page pointed at a stale ngrok tunnel URL).

## Possible next steps

- Role-based access control (e.g. an admin role that can see all users'
  data, vs. regular users limited to their own).
- Refresh tokens.
- Rate limiting on `/api/auth/login`.
