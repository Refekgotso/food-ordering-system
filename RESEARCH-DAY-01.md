# Research Day 01 - Category CRUD

## Concepts

### Q1. What does CRUD stand for?
CRUD stands for Create, Read, Update, Delete. These are the four basic
operations any application performs on data. In my Category API:
Create = POST (add a new category), Read = GET (view categories),
Update = PUT (change an existing category), Delete = DELETE (remove
a category).

### Q2. Difference between HTTP methods POST, PUT, PATCH, DELETE?
POST is used to create a new resource - the server decides the id and
returns a new record. PUT is used to fully replace an existing resource
with new data, usually requiring the full object to be sent. PATCH is
similar to PUT but only updates part of a resource, not all of it.
DELETE removes a resource permanently. In my project I use POST to add
a category, PUT to update a category's name, and DELETE to remove one.

### Q3. Correct HTTP status code for each:
a. A new category was created - 201 Created
b. A category was deleted successfully - 204 No Content
c. The id requested does not exist - 404 Not Found
d. The request body is missing a required field - 400 Bad Request
e. The user is logged in but not allowed - 403 Forbidden

### Q4. Difference between @RequestBody, @RequestParam, @PathVariable
@RequestBody reads data from the body of the request, usually JSON,
like when I send {"name": "Snacks"} when creating a category.
@RequestParam reads data from the query string of the URL, for example
?sort=name. @PathVariable reads data directly from the URL path itself,
like the {id} in /api/category/{id}, which I used in my
getCategoryById, updateCategory, and deleteCategory methods.

### Q5. What is Jakarta Bean Validation? Explain @Valid, @NotBlank, @Size
Jakarta Bean Validation is a way to automatically check that incoming
data meets certain rules before it reaches the business logic. @Valid
tells Spring to run these checks on the object before the method body
executes - I added this to my controller's addCategory and
updateCategory parameters. @NotBlank checks that a String is not null,
not empty, and not just whitespace - I used this on the name field so
an empty category name gets rejected. @Size checks the length of a
String falls within a min and max range - I set min=2, max=50 so names
can't be too short or too long.

### Q6. Why return a DTO and not the entity itself? Give 2 reasons
First, returning the entity directly would expose the internal database
structure to the outside world, including fields that clients don't
need or shouldn't see, which is a security and design risk. Second, a
DTO gives flexibility to shape the API response independently from the
database table - if the entity changes later, the DTO can stay stable
for clients, or vice versa, without breaking the contract.

### Q7. What is Optional<T>? Why does findById return Optional?
Optional<T> is a container object that may or may not hold a value of
type T. It exists to avoid using null directly, which often causes
NullPointerExceptions when forgotten. findById returns an Optional
because a category with a given id might not exist in the database -
instead of returning null and risking a crash, Optional forces the
calling code to explicitly handle the "not found" case, which is why I
used .orElseThrow() in CategoryServiceImpl to throw my custom
CategoryNotFoundException when nothing is found.

## Self-Quiz

### Q1. Why ResponseEntity instead of returning the object?
ResponseEntity lets me control the full HTTP response, not just the
body. I can set the exact status code (like 201 Created for my
addCategory endpoint), and add headers if needed. Returning a plain
object only gives Spring's default status code, usually 200, which
isn't always correct - for example a "create" action should
return 201, not 200.

### Q2. What status should a successful DELETE return? Why?
204 No Content. This tells the client the action succeeded but there is
no body to return, since the resource no longer exists. Returning 200
would imply there's content in the response, which doesn't make sense
after something has been deleted.

### Q3. Update only one field - PUT or PATCH? Defend your answer.
PATCH is the better choice for updating only one field. PUT is meant to
replace the entire resource, so technically the client should send the
full object even if only one field changed. PATCH is designed for
partial updates, so it only requires the field(s) being changed. In my
project I used PUT for simplicity since Category only has a name field
to update, but in a resource with many fields, PATCH would be more
appropriate for single-field changes.

### Q4. What happens if you forget @Valid on the controller?
If @Valid is missing, the @NotBlank and @Size annotations on the DTO
are never triggered - Spring will not run the validation checks before
the method executes. This means invalid data, like an empty or overly
long name, would be accepted and passed straight to the service and
database, bypassing the rules I defined and potentially causing
bad data or errors further down the line.

### Q5. Why must update/delete have {id} in the URL but create does not?
Update and delete need to target one specific, existing resource, so
the server needs to know exactly which record to act on - that's what
{id} in the URL provides. Create is different because the resource
doesn't exist yet; there is no id to reference until after the database
generates one, so the id isn't part of the request, only the response.