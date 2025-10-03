# FriendlyId jOOQ Integration

jOOQ converter for transparent UUID to FriendlyId conversion in database queries.

## Overview

This module provides a jOOQ `Converter` that allows you to work with FriendlyId strings in your Java code while storing UUIDs in the database. jOOQ will automatically handle the conversion between the two representations.

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
          <userType>java.lang.String</userType>
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
// Query using FriendlyId
String friendlyId = "5wbwf6yUxVBcr48AMbz9cb";
UserRecord user = create
    .selectFrom(USER)
    .where(USER.ID.eq(friendlyId))  // Automatically converted from FriendlyId to UUID
    .fetchOne();

// Get FriendlyId from result
String userId = user.getId();  // Returns FriendlyId string, not UUID

// Insert with FriendlyId
create.insertInto(USER)
    .set(USER.ID, FriendlyId.createFriendlyId())
    .set(USER.NAME, "John Doe")
    .execute();
```

## How It Works

The `FriendlyIdConverter` implements `org.jooq.Converter<UUID, String>`:

- **Database Type (fromType)**: `UUID` - the actual column type in your database
- **User Type (toType)**: `String` - the FriendlyId representation in your Java code
- **Conversion**: Bidirectional conversion between UUID and FriendlyId strings

## Benefits

- **URL-Friendly**: Use human-readable, Base62-encoded IDs in your API
- **Type Safety**: jOOQ handles conversion automatically
- **Database Efficiency**: Store compact UUIDs in the database
- **Transparent**: No manual conversion needed in your application code

## See Also

- [jOOQ Converters Documentation](https://www.jooq.org/doc/latest/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-forced-types/codegen-database-forced-types-converter/)
- [FriendlyId Core Library](../friendly-id/)
