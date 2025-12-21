# FriendlyId JPA Integration

JPA AttributeConverter for transparent UUID to FriendlyId conversion in entity mappings.

## Overview

This module provides a JPA `AttributeConverter` that allows you to work with FriendlyId value objects in your JPA entities while storing UUIDs in the database. JPA will automatically handle the conversion between the two representations.

The `FriendlyId` value object is **memory-efficient**, storing the UUID internally (16 bytes) and computing the FriendlyId string representation only when needed (e.g., `toString()`). This is more efficient than storing String representations (~40-50 bytes).

## Maven Dependency

```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-jpa</artifactId>
    <version>1.1.1-SNAPSHOT</version>
</dependency>
```

## Automatic Usage (Recommended)

The converter is **automatically applied** to all FriendlyId attributes thanks to `@Converter(autoApply = true)`. No configuration needed!

```java
import com.devskiller.friendly_id.type.FriendlyId;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private FriendlyId id;  // Automatically converted to/from UUID

    private String name;

    // getters/setters
}
```

## Usage Examples

### Creating Entities

```java
// Create with random FriendlyId
User user = new User();
user.setId(FriendlyId.random());
user.setName("John Doe");
em.persist(user);

// Create from FriendlyId string
User user = new User();
user.setId(FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb"));
user.setName("Jane Doe");
em.persist(user);
```

### Querying

```java
// JPQL query
FriendlyId userId = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");
User user = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
    .setParameter("id", userId)
    .getSingleResult();

// Find by ID
User user = em.find(User.class, FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb"));

// Criteria API
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<User> query = cb.createQuery(User.class);
Root<User> root = query.from(User.class);
query.where(cb.equal(root.get("id"), userId));
List<User> users = em.createQuery(query).getResultList();
```

### Native Queries

For native queries, use the UUID directly:

```java
FriendlyId userId = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");
User user = em.createNativeQuery(
        "SELECT * FROM users WHERE id = ?",
        User.class)
    .setParameter(1, userId.uuid())  // Use .uuid() for native queries
    .getSingleResult();
```

### Pretty Printing

```java
User user = em.find(User.class, someId);
System.out.println("User ID: " + user.getId());
// Prints: User ID: 5wbwf6yUxVBcr48AMbz9cb
```

## Manual Application (Optional)

If you disabled autoApply or need explicit control:

```java
@Entity
public class User {
    @Id
    @Convert(converter = FriendlyIdConverter.class)
    private FriendlyId id;

    private String name;
}
```

## Spring Data JPA Example

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, FriendlyId> {

    // Derived query methods work automatically
    Optional<User> findByName(String name);

    // Custom queries with FriendlyId parameters
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByFriendlyId(@Param("id") FriendlyId id);
}

// Usage
FriendlyId id = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");
Optional<User> user = userRepository.findById(id);
```

## How It Works

The `FriendlyIdConverter` implements `jakarta.persistence.AttributeConverter<FriendlyId, UUID>`:

- **Database Column Type**: `UUID` (16 bytes)
- **Entity Attribute Type**: `FriendlyId` value object (~28 bytes in memory)
- **Conversion**: Bidirectional and automatic

## Memory Efficiency

| Type | Memory Usage | Notes |
|------|-------------|-------|
| UUID | 16 bytes | Database storage |
| FriendlyId | ~28 bytes | 16 bytes UUID + ~12 bytes object header |
| String | ~40-50 bytes | FriendlyId as String (avoided) |

**Result**: ~30-40% memory savings compared to storing FriendlyId as String

## Benefits

- **Zero Configuration**: Works automatically with `autoApply = true`
- **Memory Efficient**: Store UUIDs internally, compute strings only when needed
- **Type Safety**: Strong typing prevents mixing UUIDs with FriendlyIds
- **Database Efficiency**: Store compact UUIDs in the database
- **Transparent**: No manual conversion in entity code
- **Pretty Printing**: Automatic FriendlyId string representation via `toString()`
- **Spring Data Compatible**: Works seamlessly with Spring Data JPA repositories

## Database Schema

The database column should be UUID type:

```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);
```

## See Also

- [JPA AttributeConverter Documentation](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/attributeconverter)
- [FriendlyId Core Library](../friendly-id/)
- [FriendlyId Value Object](../friendly-id/src/main/java/com/devskiller/friendly_id/type/FriendlyId.java)
- [FriendlyId jOOQ Integration](../friendly-id-jooq/)
