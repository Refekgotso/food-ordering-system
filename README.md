# Food Ordering System

## Project Investigation

### 1. What is Spring Boot?
Spring Boot is a Java framework that makes it easy to create
web applications and REST APIs. It removes the need for complex
configuration by providing defaults, allowing developers to
focus on writing business logic instead of setup.

### 2. What is Maven?
Maven is a build and dependency management tool for Java projects.
It automatically downloads the libraries your project needs and
builds your application using a file called pom.xml.

### 3. What is the purpose of pom.xml?
pom.xml (Project Object Model) is the configuration file for Maven.
It defines the project dependencies, plugins, and build settings.
It tells Maven what libraries to download and how to build the project.

### 4. What is the purpose of application.properties?
application.properties is a configuration file in Spring Boot.
It stores settings such as database connection details, server port,
and other application configurations in a key-value format.

### 5. What does @SpringBootApplication do?
@SpringBootApplication is an annotation that marks the main class
of a Spring Boot application. It enables auto-configuration,
component scanning, and configuration for the entire application.

### 6. Why do developers use dependency management tools such as Maven?
Developers use Maven to automatically manage external libraries,
avoid version conflicts, and simplify the build process. Without it,
developers would have to manually download and configure every library.

### 7. What is a REST API?
A REST API (Representational State Transfer) is a way for applications
to communicate over the internet using HTTP methods such as GET, POST,
PUT, and DELETE. It allows the frontend and backend to exchange data.

### 8. What is JSON?
JSON (JavaScript Object Notation) is a lightweight format for storing
and exchanging data. It is easy for humans to read and write, and easy
for machines to parse. REST APIs commonly use JSON to send and receive data.

### 9. What is Dependency Injection?
Dependency Injection is a design pattern where an object receives its
dependencies from an external source rather than creating them itself.
In Spring Boot, the framework automatically injects required objects,
making code more flexible, testable, and maintainable.

## Package Structure

### controller
Handles incoming HTTP requests (GET, POST, PUT, DELETE).
Acts as the entry point for all API calls.

### service
Contains the business logic of the application.
Processes data between the controller and repository.

### repository
Manages all database operations.
Communicates directly with the database using JPA.

### entity
Represents database tables as Java classes.
Each entity maps to a table in the database.

### dto (Data Transfer Object)
Shapes the data transferred between layers.
Prevents exposing the full database structure externally.

### config
Holds configuration classes for the application.
Examples include database and security configurations.

### exception
Handles errors and custom exceptions in one place.
Returns clean error messages instead of crashes.

## Database
Database: food_ordering_db
Table: category

## Project Structure
All packages are located under food_ordering_system:
controller, service, repository, entity, dto, config, exception

## Research Questions Completed
- Researched and documented all package purposes
- Created food_ordering_db database
- Created category table with AUTO_INCREMENT
- Inserted 4 category records
- All appearing

### Endpoints
| Method | URL                       | Body         |
  |--------|---------------------------|--------------|
| POST   | /api/categories           | { "name" }   |
| GET    | /api/categories           | -            |
| GET    | /api/categories/{id}      | -            |
| PUT    | /api/categories/{id}      | { "name" }   |
| DELETE | /api/categories/{id}      | -            |

## API Response Format

Every endpoint returns a consistent JSON response shape:

```json
{
    "statusCode": 200,
    "message": "Category retrieved",
    "data": {
        "id": 1,
        "name": "Fast Food"
    },
    "timestamp": "2026-06-18T08:42:11"
}
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| statusCode | int | HTTP-style status code (200, 201, 404, 400) |
| message | String | Human-readable description of the result |
| data | T | The actual payload (absent on error responses) |
| timestamp | LocalDateTime | Exact date and time the response was generated |

### Error Response Example

```json
{
    "error": "Not Found",
    "message": "Category not found with id: 9999",
    "timestamp": "2026-06-18T08:42:11",
    "status": 404
}
```
## Menu API

All Menu endpoints are under `/api/menu`.

### Endpoints

| Method | Path             | Description                          | Query Params |
|--------|------------------|--------------------------------------|---------------|
| POST   | /api/menu        | Create a new menu item               | -             |
| GET    | /api/menu        | List menus (filter, search, paginate, sort) | `categoryId`, `search`, `page`, `size`, `sort` |
| GET    | /api/menu/{id}   | Get a single menu item by id         | -             |
| PUT    | /api/menu/{id}   | Update a menu item by id             | -             |
| DELETE | /api/menu/{id}   | Delete a menu item by id             | -             |

### Query Parameters (GET /api/menu)

All parameters are optional and can be combined in any order.

| Param        | Type    | Description                                      | Default |
|--------------|---------|---------------------------------------------------|---------|
| `categoryId` | Long    | Filter to a specific category                     | -       |
| `search`     | String  | Case-insensitive partial match on menu name        | -       |
| `page`       | Integer | Zero-based page number                             | 0       |
| `size`       | Integer | Items per page                                     | 10      |
| `sort`       | String  | Field and direction, e.g. `price,asc`              | -       |

### Example Request
### Example Response (Page<MenuDto>)

```json
{
  "statusCode": 200,
  "message": "Menus retrieved",
  "data": {
    "content": [
      {
        "id": 5,
        "name": "Pepperoni Pizza",
        "description": "Classic pepperoni",
        "price": 99.50,
        "imageUrl": "https://placehold.co/300",
        "categoryId": 3,
        "categoryName": "Soft Drinks"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "number": 0,
    "size": 5,
    "first": true,
    "last": true,
    "empty": false
  },
  "timestamp": "2026-07-07T11:05:34.602783"
}
```

### Error Responses

| Scenario                                   | Status |
|---------------------------------------------|--------|
| Category not found (create/update)          | 404    |
| Menu not found (get/update/delete)           | 404    |
| Validation failure (missing name, negative price, etc.) | 400    |
| Deleting a category that still has menus     | 409    |
