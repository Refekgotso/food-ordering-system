# Research Day 03 - Menu Feature

## Concepts

### Q1. What is JPA? What is Hibernate? How are they related?
JPA (Java Persistence API) is a specification that defines how Java
objects should be mapped to relational database tables. It is just a
set of rules and interfaces, not an actual implementation. Hibernate
is the most popular implementation of JPA - it does the actual work
of converting Java objects to SQL queries and back. In my project,
Spring Boot uses Hibernate as the JPA provider automatically, so when
I annotate a class with @Entity, Hibernate handles all the database
communication behind the scenes.

### Q2. What is the difference between @Entity and @Table?
@Entity tells JPA that this Java class represents a database table and
should be managed by the persistence context. @Table is optional and
lets you customize the actual table name in the database. If I only
use @Entity, JPA uses the class name as the table name by default.
In my Menu class I use both - @Entity marks it as a managed entity,
and @Table(name = "menus") ensures the table is called "menus" instead
of "menu".

### Q3. What is a foreign key? What is @ManyToOne? Give 2 real-world examples.
A foreign key is a column in one table that references the primary key
of another table, creating a relationship between them. @ManyToOne
is a JPA annotation that maps this relationship - it means many records
in one table can relate to one record in another table.
Example 1: Many menu items can belong to one category (many menus,
one category).
Example 2: Many orders can belong to one customer (many orders,
one customer).

### Q4. What does @JoinColumn(name = "category_id") do?
@JoinColumn tells JPA which column in the current table holds the
foreign key reference. In my Menu entity, @JoinColumn(name =
"category_id") means the "menus" table will have a column called
"category_id" that stores the id of the related Category. Without
this annotation, JPA would generate a default column name which might
not match what I want.

### Q5. Why store price as BigDecimal and not double?
double is a floating point type that cannot represent all decimal
numbers exactly due to how binary floating point works. For example,
0.1 + 0.2 in double gives 0.30000000000000004, not 0.3. This kind of
rounding error is unacceptable when dealing with money. BigDecimal
stores numbers with exact precision, so 49.99 stays exactly 49.99.
This is why I use BigDecimal for the price field in my Menu entity.

### Q6. What does FetchType LAZY vs EAGER mean? What is the default for @ManyToOne?
EAGER means the related entity is loaded from the database immediately
when the parent entity is loaded, even if you don't need it. LAZY
means the related entity is only loaded when you actually access it.
The default for @ManyToOne is EAGER, meaning when I load a Menu, JPA
will automatically also load the related Category. LAZY is generally
preferred for performance since it avoids unnecessary database queries.

### Q7. What is the N+1 query problem?
The N+1 problem happens when loading a list of N entities each
triggers an additional query to load a related entity. For example,
if I load 100 menu items and each one triggers a separate query to
load its category, that's 1 query for the menus plus 100 queries for
the categories = 101 queries total. This is very inefficient. The fix
is to use JOIN FETCH in JPQL or configure fetching strategies to load
related data in a single query.

### Q8. What is dependency injection? Constructor injection vs field injection — which is preferred and why?
Dependency injection is when an object receives its dependencies from
an external source (Spring) rather than creating them itself. Field
injection uses @Autowired directly on the field, while constructor
injection passes dependencies through the constructor. Constructor
injection is preferred because it makes dependencies explicit and
required, makes the class easier to test (you can pass mock objects
directly), and works without reflection. Lombok's
@RequiredArgsConstructor generates a constructor for all final fields,
making constructor injection clean and concise.

### Q9. What does @RequiredArgsConstructor (Lombok) do?
@RequiredArgsConstructor generates a constructor that takes all
fields marked as final or @NonNull as parameters. In my service
classes, I declare my repository dependencies as private final fields
and annotate the class with @RequiredArgsConstructor - Lombok then
generates the constructor automatically, and Spring uses that
constructor to inject the dependencies. This is cleaner than writing
the constructor manually or using @Autowired on each field.

### Q10. What is the role of the SERVICE layer? Why must it be separate from the controller?
The service layer contains the business logic of the application -
rules, calculations, validations, and orchestration between different
repositories. It must be separate from the controller because
controllers should only handle HTTP concerns (receiving requests,
returning responses), while services handle what actually happens with
the data. Keeping them separate makes the code easier to test, reuse,
and maintain - for example, the same service method can be called from
a REST controller, a scheduled job, or a message listener.

### Q11. Why MUST you validate that categoryId exists before saving a menu?
If I save a menu with a categoryId that doesn't exist in the categories
table, the database will reject it with a foreign key constraint
violation, causing an ugly 500 error. By checking first using
categoryRepository.findById(categoryId) and throwing a clean
CategoryNotFoundException if it's not found, I give the client a
meaningful 404 error instead of a raw database error, making the API
much more user-friendly and predictable.

### Q12. Difference between save() and saveAndFlush()?
save() persists the entity and schedules the SQL INSERT or UPDATE, but
the actual SQL may not be sent to the database immediately - it might
be batched. saveAndFlush() persists the entity AND immediately flushes
the changes to the database, forcing the SQL to execute right away.
saveAndFlush() is useful when you need to immediately see the effect
of a save in the same transaction, for example when you need the
generated id straight away.

### Q13. Why write private mapper methods (entity <-> dto)?
Mapper methods keep the conversion logic between entities and DTOs in
one place instead of repeating it throughout the service class. If the
entity or DTO changes, I only need to update the mapper method in one
place rather than hunting through multiple methods. They also keep
individual service methods cleaner and easier to read, since the
mapping details are hidden behind a descriptive method name like
mapToDto() or mapToEntity().

## Self-Quiz

### Q1. Why didn't we add @OneToMany on Category for menus?
We use a unidirectional relationship - Menu knows about Category, but
Category doesn't need to know about its menus. Adding @OneToMany on
Category would make it bidirectional, which adds complexity and can
cause performance issues and infinite recursion when serializing to
JSON. Since we don't need to navigate from Category to its menus in
this application, keeping it unidirectional is simpler and cleaner.

### Q2. What would ddl-auto = create-drop do? When would you use it?
create-drop creates the schema when the application starts and drops
it when it stops. This means all data is lost every time the
application restarts. It's useful only in development or testing
environments where you want a fresh database state every time, never
in production.

### Q3. If you delete a Category that has menus, what happens by default?
By default, the database will reject the deletion with a foreign key
constraint violation error, since there are menu rows that reference
that category. To handle this, you would need to either delete the
menus first, set up cascade delete, or set the foreign key to null
before deleting the category.

### Q4. Why is BigDecimal better than double for storing money values?
BigDecimal stores exact decimal values with no floating point rounding
errors, which is critical for financial calculations. double uses
binary floating point which cannot represent many decimal fractions
exactly, leading to tiny but unacceptable errors when adding,
subtracting, or comparing money amounts. BigDecimal also lets you
control rounding mode explicitly, which is important for things like
tax calculations or currency conversions.