# Library Management System (Microservices)

A **Library Management System** built as a set of microservices that demonstrates:
- Spring IoC / Dependency Injection
- Java Stream API analytics
- Intermediate native SQL query in `borrowing-service`
- Database-per-service PostgreSQL setup
- Dockerized services orchestrated with Docker Compose

## Services

- `book-service` (`:8081`) - book metadata
- `member-service` (`:8082`) - library member data
- `borrowing-service` (`:8083`) - checkout/return flow + native SQL stats
- `reporting-service` (`:8084`) - cross-service analytics using Java Streams

Each service is a Spring Boot application, uses PostgreSQL as its own database (database‑per‑service), and communicates via REST APIs. Docker Compose orchestrates all services and databases.

## Run with Docker Compose

```bash
docker compose up --build
```

## Example API calls

Create book:
```bash
curl -X POST http://localhost:8081/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Code","author":"Robert C. Martin","genre":"Software","isbn":"9780132350884","totalCopies":5}'
```

Create member:
```bash
curl -X POST http://localhost:8082/api/members \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com","membershipStatus":"ACTIVE"}'
```

Upsert member reference into borrowing database (for native SQL join):
```bash
curl -X POST http://localhost:8083/api/borrowings/member-ref \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Alice"}'
```

Checkout:
```bash
curl -X POST http://localhost:8083/api/borrowings/checkout \
  -H "Content-Type: application/json" \
  -d '{"memberId":"1","bookId":"1","dueDate":"2026-05-05"}'
```

Return:
```bash
curl -X POST "http://localhost:8083/api/borrowings/1/return?returnDate=2026-05-10"
```

Native SQL member stats:
```bash
curl "http://localhost:8083/api/borrowings/stats/members?startDate=2026-01-01&minBorrows=0"
```

Top books report (Java Streams):
```bash
curl "http://localhost:8084/api/reports/top-books?topN=5"
```
