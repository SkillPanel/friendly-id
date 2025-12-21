# FriendlyId jOOQ Integration

jOOQ converter for transparent UUID to FriendlyId conversion in database queries.

## Overview

This module provides a jOOQ `Converter` that allows you to work with FriendlyId value objects in your Java code while storing UUIDs in the database. jOOQ will automatically handle the conversion between the two representations.

The `FriendlyId` value object is **memory-efficient**, storing the UUID internally (16 bytes) and computing the FriendlyId string representation only when needed (e.g., `toString()`). This is more efficient than storing String representations (~40-50 bytes).

## Maven Dependency

```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-jooq</artifactId>
    <version>1.1.1-SNAPSHOT</version>
</dependency>
```

## Usage with jOOQ Code Generator

Configure the converter in your jOOQ code generation configuration to apply it to specific columns or all UUID columns:

```xml
<configuration>
  <generator>
    <database>
      <forcedTypes>
        <forcedType>
          <userType>com.devskiller.friendly_id.type.FriendlyId</userType>
          <converter>com.devskiller.friendly_id.jooq.FriendlyIdConverter</converter>
          <includeExpression>.*\.ID</includeExpression>
          <includeTypes>UUID</includeTypes>
        </forcedType>
      </forcedTypes>
    </database>
  </generator>
</configuration>
```

## Example

```java
import com.devskiller.friendly_id.type.FriendlyId;

// Query using FriendlyId
FriendlyId friendlyId = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");
UserRecord user = create
    .selectFrom(USER)
    .where(USER.ID.eq(friendlyId))  // Automatically converted to UUID for database
    .fetchOne();

// Get FriendlyId from result
FriendlyId userId = user.getId();  // Returns FriendlyId value object
String userIdString = userId.toString();  // Get string representation when needed

// Insert with FriendlyId
create.insertInto(USER)
    .set(USER.ID, FriendlyId.random())
    .set(USER.NAME, "John Doe")
    .execute();

// FriendlyId prints nicely
System.out.println("User ID: " + userId);  // Prints: User ID: 5wbwf6yUxVBcr48AMbz9cb
```

## How It Works

The `FriendlyIdConverter` implements `org.jooq.Converter<UUID, FriendlyId>`:

- **Database Type (fromType)**: `UUID` - the actual column type in your database (16 bytes)
- **User Type (toType)**: `FriendlyId` - value object wrapping UUID in your Java code (~28 bytes)
- **Conversion**: Bidirectional conversion between UUID and FriendlyId value objects

## Memory Efficiency

| Type | Memory Usage | Notes |
|------|-------------|-------|
| UUID | 16 bytes | Database storage |
| FriendlyId | ~28 bytes | 16 bytes UUID + ~12 bytes object header |
| String | ~40-50 bytes | FriendlyId as String (previous approach) |

**Result**: ~30-40% memory savings compared to storing FriendlyId as String

## Benefits

- **Memory Efficient**: Store UUIDs internally, compute strings only when needed
- **URL-Friendly**: Automatic conversion to human-readable, Base62-encoded IDs
- **Type Safety**: Strong typing prevents mixing UUIDs with FriendlyIds
- **Database Efficiency**: Store compact UUIDs in the database
- **Transparent**: No manual conversion needed in your application code
- **Pretty Printing**: Automatic FriendlyId string representation via `toString()`

## See Also

- [jOOQ Converters Documentation](https://www.jooq.org/doc/latest/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-forced-types/codegen-database-forced-types-converter/)
- [FriendlyId Core Library](../friendly-id/)
- [FriendlyId Value Object](../friendly-id/src/main/java/com/devskiller/friendly_id/type/FriendlyId.java)
