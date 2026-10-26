# CraveCart — JDBC version

Java 17 + Spring Boot 3.5 + Spring JDBC (`JdbcTemplate`) + MySQL + HTML/CSS/JS + Docker Compose.
This project intentionally does **not** use Spring Data JPA or Hibernate.

## Run in VS Code on Windows
1. Install Docker Desktop and start it.
2. Install VS Code and the Extension Pack for Java (recommended).
3. Extract this ZIP, then in VS Code choose File > Open Folder and select `cravecart`.
4. In VS Code, open Terminal > New Terminal.
5. Run `docker compose up --build`.
6. Wait for app startup and open http://localhost:8080.

No separate Java or MySQL install is needed for the Docker run. Java 17 is inside the Docker build image.

Stop: Ctrl+C, or another terminal: `docker compose down`.
Delete the database volume (permanently deletes saved demo orders): `docker compose down -v`.

## Spring Initializr
Maven; Java; Spring Boot 3.5.x stable; Group `com.cravecart`; Artifact `cravecart`; Package `com.cravecart`; Jar; Properties; Java 17.
Dependencies: Spring Web, JDBC API, MySQL Driver, Validation. Do not add Spring Data JPA.

## APIs
- GET `/api/foods`
- GET `/api/foods?search=pizza`
- GET `/api/foods?category=Pizza`
- POST `/api/foods`
- PUT `/api/foods/{id}`
- DELETE `/api/foods/{id}`
- GET `/api/orders`
- GET `/api/orders/{id}`
- POST `/api/orders`
- PUT `/api/orders/{id}/status?status=PREPARING`
- DELETE `/api/orders/{id}`
- GET `/api/orders/queue`

Example order:
```json
{
  "customerName": "Alex Morgan",
  "customerEmail": "alex@example.com",
  "deliveryAddress": "12 Garden Road, Bengaluru",
  "items": [
    {"foodItemId": 1, "quantity": 2},
    {"foodItemId": 2, "quantity": 1}
  ]
}
```

## JDBC explanation for presentation
- `JdbcTemplate` executes SQL using the MySQL JDBC driver.
- `FoodItemRepository` has SELECT, INSERT, UPDATE, and DELETE SQL statements.
- `CustomerOrderRepository` inserts order and order-item rows, reads orders, and updates status.
- `schema.sql` creates tables and `data.sql` inserts sample food.
- The order queue is an in-memory FIFO demonstration and resets if the app restarts.
- Demo billing: 5% tax plus ₹30 delivery charge. No payment gateway, login, or real delivery integration.
