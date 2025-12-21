[![Build Status](https://travis-ci.org/Devskiller/friendly-id.svg?branch=master)](https://travis-ci.org/Devskiller/friendly-id)  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.devskiller.friendly-id/friendly-id/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.devskiller.friendly-id/friendly-id)  [![Coverage Status](https://coveralls.io/repos/github/Devskiller/friendly-id/badge.svg?branch=master)](https://coveralls.io/github/Devskiller/friendly-id?branch=master) 

FriendlyID (Java, [Swift](https://github.com/kdubb/SwiftFriendlyId), [Rust](https://github.com/mariuszs/friendly_id), [Go](https://github.com/mariuszs/friendlyid-go))
==


What is the FriendlyID library?
--
The FriendlyID library converts a given UUID (with 36 characters) to a URL-friendly ID (a "FriendlyID") which is based on Base62 (with a maximum of 22 characters), as in the example below:


    UUID                                        Friendly ID
    
    c3587ec5-0976-497f-8374-61e0c2ea3da5   ->   5wbwf6yUxVBcr48AMbz9cb
    |                                           |                              
    36 characters                               22 characters or less

In addition, this library allows to:
 

* convert from a FriendlyID back to the original UUID; and
* create a new, random FriendlyID

Why use a FriendlyID?
--
Universal Unique IDs (UUIDs) provide a non-sequential and unique identifier that can be generated separately from the source database. As a result, it is not possible to guess either the previous or next identifier. That's great, but, to achieve this level of security, a UUID is long (128 bits long) and looks ugly (36 alphanumeric characters including four hyphens which are added to make it easier to read the UUID), as in this example: `123e4567-e89b-12d3-a456-426655440000`.

Such a format is:

* difficult to read (especially if it is part of a URL)
* difficult to remember
* cannot be copied with just two mouse-clicks (you have to select manually the start and end positions)
* can easily become broken across lines when it is copied, pasted, edited, or sent.


Our FriendlyID Java library solves these problems by converting a given UUID using Base62 with alphanumeric characters in the range [0-9A-Za-z] into a FriendlyId which consists of a maximum of 22 characters (but in fact often contains fewer characters).

Supported languages
--

Curently FriendlyId supports Java (this project) and
 * [Swift](https://github.com/kdubb/SwiftFriendlyId) language (thanks to [Kevin Wooten](https://github.com/kdubb))
 * [Rust](https://github.com/mariuszs/friendly_id) [![Version](https://img.shields.io/crates/v/friendly_id.svg?style=social&logo=appveyor)](https://crates.io/crates/friendly_id)
 * [Go](https://github.com/mariuszs/friendlyid-go) 

Tools
--

There are available CLI converters for many platforms.

* https://github.com/mariuszs/rust-friendlyid (also available in RPM and DEB format)
* https://github.com/kdubb/SwiftFriendlyId#command-line

## Use cases

### Basic (returning a user in a database)


Let us assume that a method in the controller for returning users requires the relevant UUID in order to find a given user in a database, as in this example:

```java
@GetMapping("/users/{userId}") 
public User getUser(@PathVariable UUID userId) {
        [implementation deleted]
}
```

Without using the Friendly ID library, you could access a given user as follows:

```bash
curl http://localhost:8080/users/c3587ec5-0976-497f-8374-61e0c2ea3da5
```


After adding the FriendlyID library, the controller method itself does not change, but you would be able to access a given user using the relevant FriendlyID as follows: 

```bash
curl http://localhost:8080/users/5wbwf6yUxVBcr48AMbz9cb
```



In addition, if a given document returned by such a method contains objects of type UUID, those IDs will also be shortened into FriendlyID format.


### Advanced (Optimizing testing)

 
 The FriendlyID library makes it possible to define for UUIDs values which are easy to read. By using names instead of hard-to-remember UUIDs, you can write much simpler tests for your code, for example:
 
```java
@Test 
public void shouldGetUser() { 
    mockMvc.perform(get("/users/{userId}", "John")) 
        .andExpect(status().isOk()) 
        .andExpect(content().contentType("application/json")) 
        .andExpect(jsonPath("$.uuid", is("John"))); 
} 
```

In the above example, the variable "John" is decoded by the library to the correct UUID, in this case, `00000000-0000-0000-0000-000000a69efb`. In this way, you can give a variable in a test class a truly meaningful value and, as a result, an assertion which refers to that variable becomes exceptionally easy to understand in your test program.


FriendlyID library 
--

Dependencies
---

```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```

Usage
---

```java
FriendlyId.createFriendlyId();
```

This creates a new, random FriendlyID, for example: `5wbwf6yUxVBcr48AMbz9cb`

```java
FriendlyId.toFriendlyId(UUID.fromString("c3587ec5-0976-497f-8374-61e0c2ea3da5"));
```

This converts a UUID in the form of a string to a FriendlyID, for example: `5wbwf6yUxVBcr48AMbz9cb`


 ```java
FriendlyId.toUuid("5wbwf6yUxVBcr48AMbz9cb");
```

This converts a FriendlyID to its UUID, for example: `c3587ec5-0976-497f-8374-61e0c2ea3da5`


Notes
--
	
* As every *UUID* is a 128-bit number, a *FriendlyID* can also store only a 128-bit number.
* If a FriendlyID has any leading zeros, those leading zeros are ignored - for example, `00cafe` is treated as `cafe`.


## Integrations

- [Spring Boot integration](#Spring-Boot-integration)
- [Jackson integration](#Jackson-integration)
- [jOOQ integration](#jOOQ-integration)
- [JPA integration](#JPA-integration)
- [OpenFeign integration](#OpenFeign-integration)

### Spring Boot integration

The FriendlyID library includes a Spring configuration to make it easy to add shorter IDs to an application. With a typical application based on Spring Boot, for your controllers to be able to use FriendlyIDs when communicating with the outside world, just add one new starter dependency as follows:

```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-spring-boot-starter</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```
    
Let us assume that you'll use this sample application:

```java
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/bars/{bar}")
    public Bar getBar(@PathVariable UUID bar) {
        return new Bar(UUID.randomUUID());
    }

    @Value
    class Bar {
        private final UUID id;
    }
}  
```   
    
This command: `curl http://localhost:8080/bars/5fD1KwsxRcGhBqWNju0jzt` 

will result in the following output: 
```json
{"id":"52OMXhWiAqUWwII0c97Svl"}
```    

In this case, `Bar` is a POJO class which is converted by Spring MVC to a JSON document. This `Bar` object has one field of type UUID, and this field is output to the JSON document as a FriendlyID instead of a UUID. Although the application uses the relevant UUID internally, from an external point of view, only the FriendlyID is visible.    

### Jackson integration    


First, add the following Jackson module dependency:
```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-jackson-datatype</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```
Then register the `FriendlyIdModule` module as follows:

```java
ObjectMapper mapper = new ObjectMapper()
   .registerModule(new FriendlyIdModule());
```

### jOOQ integration

The FriendlyID library provides a jOOQ converter for seamless integration with jOOQ's code generation and type-safe queries.

First, add the dependency:
```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-jooq</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```

Configure the converter in your jOOQ code generation configuration:
```xml
<forcedTypes>
    <forcedType>
        <userType>com.devskiller.friendly_id.type.FriendlyId</userType>
        <converter>com.devskiller.friendly_id.jooq.FriendlyIdConverter</converter>
        <includeExpression>.*\.id</includeExpression>
        <includeTypes>UUID</includeTypes>
    </forcedType>
</forcedTypes>
```

This automatically converts UUID database columns to `FriendlyId` value objects in your generated jOOQ records.

### JPA integration

The FriendlyID library includes a JPA `AttributeConverter` for transparent conversion between UUID database columns and `FriendlyId` value objects.

First, add the dependency:
```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-jpa</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```

The converter is automatically applied to all `FriendlyId` attributes in your entities:
```java
@Entity
public class User {
    @Id
    private FriendlyId id;
    
    private String name;
    
    // getters/setters
}
```

The `FriendlyId` value object stores UUID internally (16 bytes) and computes the FriendlyId string only when needed, making it more memory-efficient than storing strings.

### OpenFeign integration

The FriendlyID library provides automatic encoding/decoding for Spring Cloud OpenFeign clients.

First, add the dependency:
```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id-openfeign</artifactId>
    <version>2.0.0-alpha6</version>
</dependency>
```

The integration is automatically configured when Spring Cloud OpenFeign is on the classpath:
```java
@FeignClient(name = "user-service")
public interface UserClient {
    
    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable UUID id); // Sends FriendlyId string
    
    @GetMapping("/users/{id}/profile")
    ProfileDto getProfile(@PathVariable FriendlyId id); // Also works with FriendlyId value object
}
```

UUID and `FriendlyId` parameters are automatically converted to FriendlyId strings in requests, and FriendlyId strings in responses are converted back to UUID or `FriendlyId` objects.

Contributing
----------

Thinking of helping us out? We invite you to take a look at:

- Source Code: [github.com/Devskiller/friendly-id/](https://github.com/Devskiller/friendly-id)
- Issue Tracker: [github.com/Devskiller/friendly-id/issues](https://github.com/Devskiller/friendly-id/issues)


License
-------

The project is licensed under the Apache 2.0 license.
For further details, please see the [License](/LICENSE/) page. 
