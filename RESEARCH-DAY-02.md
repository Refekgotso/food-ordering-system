# Research Day 02 - Standard Response<T> Wrapper

## Concepts

### Q1. What is a Java generic type? Why is <T> useful?
A generic type lets a class or method work with different data types
without rewriting the code for each one. <T> is a placeholder for
whatever type is used when the class is actually called. In my
Response<T> class, T can be a CategoryDto, a List<CategoryDto>, or
any other type, so I only need one Response class instead of a
separate one for every type of data I return.

### Q2. What does Lombok @Builder generate behind the scenes?
@Builder generates a builder class with a method for each field,
allowing objects to be constructed step by step using chained method
calls, like Response.builder().statusCode(200).message("OK").build().
Behind the scenes Lombok creates a static nested Builder class with
setter-style methods that return the builder itself, plus a build()
method that creates the final object using all the values that were
set.

### Q3. What is the Builder design pattern? When to use it?
The Builder pattern is a way to construct complex objects step by
step, instead of using one large constructor with many parameters.
It's useful when an object has several optional fields, because it
avoids having multiple overloaded constructors or a constructor call
with confusing unnamed arguments. In my Response<T> class, I use the
builder so I can create a success response with all four fields, or
an error response with only some of them, using the same clean style.

### Q4. What is LocalDateTime? How is it different from Date?
LocalDateTime is part of Java's newer date and time API (java.time)
introduced in Java 8. It represents a date and time without any time
zone information, and it's immutable, meaning once created it can't
be changed. The older Date class is mutable, doesn't handle time
zones well, and has a confusing API with many deprecated methods.
LocalDateTime is what I use for the timestamp field in my Response
class to record exactly when a response was generated.

### Q5. Why does a consistent response format matter to frontend developers?
If every endpoint returns data in a different shape, frontend
developers have to write different parsing logic for every single
API call, which is repetitive and error-prone. A consistent format
like my Response<T> wrapper, where every response always has
statusCode, message, data, and timestamp, means the frontend can
write one reusable function to handle any response from the API,
check the status, and display the message or data accordingly.

### Q6. What does @JsonInclude(JsonInclude.Include.NON_NULL) do?
This annotation tells Jackson, the library that converts Java objects
to JSON, to leave out any field that is null when generating the
JSON response. I use this on my Response<T> class because my error()
method doesn't set a data field, so without this annotation the JSON
would show "data": null, which is unnecessary clutter. With it, the
data field simply doesn't appear at all in error responses.

### Q7. What is a static factory method? Why use Response.success(...) instead of new Response<>()?
A static factory method is a static method that creates and returns
an instance of a class, instead of using the new keyword directly. I
use Response.success(message, data) and Response.error(code, message)
because they give clear, descriptive names to what's actually
happening, rather than calling a generic constructor and manually
setting every field. They also centralize the logic for filling in
repeated values like statusCode and timestamp, so I don't have to
repeat that code in every controller method.

## Self-Quiz

### Q1. Why use generic <T> instead of Object for data field?
Using <T> keeps type safety. If data were declared as Object, the
caller would need to manually cast it back to the correct type every
time they used it, which is error-prone and can cause runtime errors.
With Response<CategoryDto>, the compiler already knows data is a
CategoryDto, so no casting is needed and mistakes are caught at
compile time instead of at runtime.

### Q2. Difference between Response<T> and ResponseEntity<T>? Can you have both at once?
ResponseEntity<T> is provided by Spring and represents the entire
HTTP response, including the status code, headers, and body. My
Response<T> is a custom class that represents just the body content,
shaped consistently with statusCode, message, data, and timestamp.
Yes, you can have both at once - in my controllers I return
ResponseEntity<Response<CategoryDto>>, where Spring's ResponseEntity
wraps the actual HTTP response, and inside its body sits my own
Response<T> object holding the structured JSON content.

### Q3. If a request fails, what statusCode does Response hold?
It depends on the type of failure - the statusCode inside my
Response object matches whatever code is passed into Response.error(),
for example 404 for a not-found category or 400 for a validation
failure. This is separate from the actual HTTP status code set on
ResponseEntity, though in my implementation I keep both values
matching so the response is consistent.

### Q4. Why add a timestamp?
A timestamp records exactly when the response was generated, which is
useful for debugging, logging, and tracing issues - for example,
matching a client-reported error to the exact moment it happened in
server logs. It also helps when comparing multiple responses over
time, like checking how fresh a piece of cached data is.