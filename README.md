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