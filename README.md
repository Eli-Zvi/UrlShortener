# URL Shortener

A RESTful URL shortening service built with Java and Spring Boot. The application allows users to create and manage shortened URLs, redirect visitors to their original destinations, and analyze link usage through click analytics.

## Features

- **URL Shortening:** Create shortened URLs using Base62 short codes.
- **URL Management:** Retrieve URL details, list URLs, and delete shortened URLs.
- **User Registration:** Register application users.
- **URL Redirection:** Redirect visitors to the original destination associated with a short code.
- **Click Analytics:** Retrieve redirection events and total click counts for individual URLs.
- **Visit Tracking:** Record redirection metadata, including timestamps, user agents, and referrers.
- **Database Persistence:** Store application data in PostgreSQL using Spring Data JPA.
- **API Security:** Configure HTTP security and endpoint access using Spring Security.
- **Unit Testing:** Test service-layer business logic using JUnit 5 and Mockito.

## Tech Stack

| Technology      | Purpose                                        |
| --------------- | ---------------------------------------------- |
| Java            | Primary programming language                   |
| Spring Boot     | Application framework                          |
| Spring MVC      | REST API development                           |
| Spring Data JPA | Database persistence                           |
| Spring Security | HTTP security and endpoint access control      |
| PostgreSQL      | Relational database                            |
| JUnit 5         | Unit testing                                   |
| Mockito         | Mocking dependencies during testing            |
| Maven           | Build and dependency management, if applicable |

## Architecture

The application uses a layered architecture to separate request handling, business logic, and persistence.

```text
Client
  |
  v
Controller Layer
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
PostgreSQL
```

The application separates functionality into three primary areas:

- **URL Management:** Creates, retrieves, and deletes shortened URLs.
- **Redirection:** Resolves short codes to their original destinations and records redirection activity.
- **Analytics:** Retrieves redirection events and calculates total clicks for shortened URLs.

Although these responsibilities are separated at the service level, some interactions between the user, URL management, and analytics functionality currently cross module boundaries. Improving these boundaries is a planned step toward a more independently deployable architecture.

## API Reference

The API runs locally at `http://localhost:8080` by default.

### User Registration

#### `POST /register`

Registers a new application user.

Example request:

```json
{
  "username": "exampleUser",
  "password": "your-password"
}
```

Example response (`201 Created`):

```json
{
  "username": "exampleUser"
}
```

The response returns the registered username.

### URL Management

#### `POST /api/`

Creates a shortened URL.

Example request:

```json
{
  "originalUrl": "https://www.example.com",
  "description": "Example website"
}
```

Example response (`201 Created`):

```json
{
  "username": "exampleUser",
  "originalUrl": "https://www.example.com",
  "shortCode": {
    "base62Num": "abc123"
  },
  "description": "Example website",
  "createdAt": "2026-10-19T18:00:00",
  "lastClicked": null
}
```

The short code and timestamps above are illustrative. Actual values depend on the application implementation. The owner may be determined by the application's security and service logic.

#### `GET /api/urls`

Retrieves the collection of URLs available to the requesting user, according to the application's access rules.

Returns an array of URL response objects.

#### `GET /api/urls/{shortCode}`

Retrieves details for a specific shortened URL.

Example:

`GET /api/urls/abc123`

Returns the URL's details, including its original destination, short code, description, and timestamps.

#### `DELETE /api/urls/{shortCode}`

Deletes a shortened URL identified by its short code.

Example:

`DELETE /api/urls/abc123`

Returns `204 No Content` on successful deletion.

### URL Redirection

#### `GET /api/redirect/{shortCode}`

Redirects the client to the original destination associated with a short code.

Example:

`GET /api/redirect/abc123`

The application resolves the short code, retrieves the original URL, and redirects the client. Redirection also provides the opportunity to record visit information for analytics.

### Analytics

#### `GET /api/analytics/{shortCode}`

Retrieves redirection events associated with a shortened URL.

Example:

`GET /api/analytics/abc123`

Each event can include:

- URL identifier
- Owner username
- Url creation timestamp
- Last-clicked timestamp
- User agent
- Referrer

#### `GET /api/analytics/totalClicks/{shortCode}`

Retrieves the total number of recorded clicks for a shortened URL.

Example:

`GET /api/analytics/totalClicks/abc123`

Example response:

```json
{
  "shortCode": "abc123",
  "totalClicks": 42
}
```

The click count is illustrative.

## Database

PostgreSQL is used to persist URL records, user information, and redirection-event data. Spring Data JPA provides the persistence layer and simplifies database interactions.

Configure the database connection through Spring Boot application properties or environment variables.

Example `application.properties` configuration:

```properties
spring.datasource.url=${URL_SHORTENER_DB_URL}
spring.datasource.username=${URL_SHORTENER_DB_USERNAME}
spring.datasource.password=${URL_SHORTENER_DB_PASSWORD}
```

Example environment variables for a local database:

```text
DB_URL=jdbc:postgresql://localhost:5432/urlshortener
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

Create the database before starting the application and ensure the configured user has the necessary permissions.

## Getting Started

### Prerequisites

- Java version compatible with the project
- PostgreSQL
- Maven or the project's Maven Wrapper

### 1. Clone the repository

```bash
git clone <repository-url>
cd <repository-directory>
```

### 2. Create the database

Connect to PostgreSQL and run:

```sql
CREATE DATABASE urlshortener;
```

### 3. Configure the application

Set the database connection properties or environment variables described above. Use credentials appropriate for your local environment.

### 4. Start the application

Using the Maven Wrapper on macOS or Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, use `mvn spring-boot:run` if Maven is installed.

The application starts on the configured server port, typically `8080`.

## Testing

The project uses JUnit 5 and Mockito to test service-layer business logic.

Unit tests verify expected behavior and use mocks to isolate services from their dependencies. This helps identify regressions and validate business rules without requiring a live database for every test.

Run the test suite using:

```bash
./mvnw test
```

## Future Improvements

- **Microservice Architecture:** Evolve the current semi-modular monolith into independently deployable services with clearly defined responsibilities and communication boundaries.
- **Module Decoupling:** Reduce cross-boundary dependencies between user management, URL management, and analytics before extracting services.
- **Docker:** Containerize the application and PostgreSQL database to simplify development and deployment.
- **Integration Testing:** Introduce Testcontainers to validate repository and database interactions against PostgreSQL.
- **Caching:** Add Redis caching to reduce database queries during URL redirection.
- **Rate Limiting:** Protect public endpoints against excessive request traffic.
- **Asynchronous Analytics:** Introduce event-driven processing to decouple click tracking from URL redirection.
- **Monitoring and Observability:** Integrate application health checks, metrics, and centralized logging to monitor service health, performance, and failures.
- **CI/CD:** Automate builds and tests through a continuous integration pipeline.
- **Security:** Implement JWT-based tokens for authentication and authorization.

## Future Features

- **Browser Extensions:** Add an extension to popular browsers, to add new urls and save favorites. 

## Project Goals

This project demonstrates practical experience with Java backend development, REST API design, relational database persistence, application security, layered architecture, analytics, and automated unit testing.
