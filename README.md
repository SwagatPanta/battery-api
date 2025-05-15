---

````markdown
# Battery API – Code Challenge

This is a Spring Boot REST API developed for the Transactive Backend Code Challenge.  
It allows saving and querying battery data by postcode range with optional filtering.

---

## How to Run

1. **Clone the project**
```bash
git clone https://github.com/YOUR_USERNAME/battery-api.git
cd battery-api
````

2. **Create MySQL database**

```sql
CREATE DATABASE batterydb;
```

3. **Configure `src/main/resources/application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/batterydb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. **Run the application**

```bash
./mvnw spring-boot:run
```

---

## API Endpoints

### POST `/api/batteries/save`

Registers a list of batteries.

### GET `/api/batteries/range`

Query batteries between postcodes.
Optional filters: `minCapacity`, `maxCapacity`

Example:

```
GET /api/batteries/range?fromPostcode=6000&toPostcode=6100&minCapacity=20000
```

---

## Testing

Run unit + integration tests:

```bash
./mvnw test
```

Generate coverage report:

```bash
./mvnw verify
open target/site/jacoco/index.html
```

---

## Features

* Bulk save batteries
* Query by postcode range
* Filter by min/max capacity
* Sorted output
* Java Streams for logic
* Logging with SLF4J
* TestContainers integration testing
* Handles large concurrent inserts

---

##  Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* MySQL
* JUnit 5, Mockito
* Lombok
* TestContainers

```

---

