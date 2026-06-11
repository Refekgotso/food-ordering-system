# Food Ordering System

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