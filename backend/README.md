# Todo API Backend (Spring Boot 3 + Java 21)

Minimal, lightweight Spring Boot 3 REST API for managing Todos with an in-memory thread-safe store.

---

## Server & Environment Prerequisites

This application does not require any database or external services. To build and run on your target server, ensure the following are installed:

1. **Java Development Kit (JDK) 21**
   - e.g., Eclipse Temurin 21, Amazon Corretto 21, or OpenJDK 21
   - Verify with: `java -version`
2. **Apache Maven 3.8+**
   - Verify with: `mvn -version`

---

## Build and Run

### 1. Build the Executable JAR
From inside the `backend` directory:
```bash
mvn clean package
```
This runs all unit/integration tests and produces a runnable fat JAR in the `target/` directory:
`target/todobackend-0.0.1-SNAPSHOT.jar`

### 2. Run the Application
```bash
java -jar target/todobackend-0.0.1-SNAPSHOT.jar
```
Or directly using Maven:
```bash
mvn spring-boot:run
```

The application will start on port `8080` (default) with an embedded Tomcat server.

---

## Configuration & Architecture

- **Package Root**: `com.tinycompute.todobackend`
- **Port**: `8080` (configured in `src/main/resources/application.properties`)
- **Persistence**: Pure in-memory storage using `ConcurrentHashMap<Long, Todo>` and `AtomicLong` inside `TodoService` for thread safety without database dependencies.
- **Security / Auth**: None (no Spring Security or Spring Data JPA dependencies).
- **CORS**: Globally enabled for all origins (`*`), headers, and HTTP methods via `CorsConfig` and `@CrossOrigin` on controllers.

---

## REST Endpoints Reference

### Health Check

| Method | Endpoint | Description | Response Example |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/health` | Service health status | `{"status":"ok"}` |

#### Example:
```bash
curl -X GET http://localhost:8080/api/health
```

---

### Todo Endpoints

Base path: `/api/todos`

| Method | Endpoint | Description | Request Body | Success Status |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/todos` | List all todos | _None_ | `200 OK` |
| `POST` | `/api/todos` | Create a new todo | `{"title": "..."}` | `201 Created` |
| `PUT` | `/api/todos/{id}` | Update existing todo | `{"title": "...", "completed": true}` | `200 OK` (or `404 Not Found`) |
| `DELETE` | `/api/todos/{id}` | Delete a todo by ID | _None_ | `204 No Content` (or `404 Not Found`) |

---

### Example cURL Commands

#### 1. Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Complete Spring Boot task"}'
```
Response (`201 Created`):
```json
{
  "id": 1,
  "title": "Complete Spring Boot task",
  "completed": false
}
```

#### 2. Get All Todos
```bash
curl -X GET http://localhost:8080/api/todos
```
Response (`200 OK`):
```json
[
  {
    "id": 1,
    "title": "Complete Spring Boot task",
    "completed": false
  }
]
```

#### 3. Update a Todo
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Complete Spring Boot task", "completed": true}'
```
Response (`200 OK`):
```json
{
  "id": 1,
  "title": "Complete Spring Boot task",
  "completed": true
}
```

#### 4. Delete a Todo
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```
Response (`204 No Content`)
