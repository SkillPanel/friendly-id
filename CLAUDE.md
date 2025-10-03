# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FriendlyID is a Java library that converts UUIDs (36 characters) to URL-friendly Base62-encoded IDs (max 22 characters). Example: `c3587ec5-0976-497f-8374-61e0c2ea3da5` → `5wbwf6yUxVBcr48AMbz9cb`

**Technology Stack:**
- Java 21
- Spring Boot 3.4.1
- JUnit 5 (Jupiter)
- Maven multi-module project

## Building and Testing

### Build Commands

```bash
# Build entire project (skipping tests)
mvn clean install -DskipTests

# Build only core modules (most common during development)
mvn clean install -DskipTests -pl friendly-id,friendly-id-jackson-datatype,friendly-id-spring-boot,friendly-id-spring-boot-starter -am

# Full build with tests
mvn clean install

# Build specific module
mvn clean install -pl friendly-id

# Run tests for specific module
mvn test -pl friendly-id

# Run single test class
mvn test -pl friendly-id -Dtest=FriendlyIdTest

# Run single test method
mvn test -pl friendly-id -Dtest=FriendlyIdTest#shouldCreateValidIdsThatConformToUuidType4
```

### Test Execution Notes

- Core library tests use `@RepeatedTest` extensively (1000 iterations) to ensure robustness
- Sample projects require Spring Boot context startup, which takes ~1-2 seconds
- Tests are designed to be fast: full test suite runs in under 10 seconds

## Architecture

### Module Structure

**Multi-module Maven project with 5 modules:**

1. **`friendly-id`** (core library)
   - Pure Java, no dependencies
   - Main classes: `FriendlyId`, `Base62`, `Url62`, `UuidConverter`
   - Conversion logic between UUID ↔ Base62 string

2. **`friendly-id-jackson-datatype`**
   - Jackson integration for JSON serialization/deserialization
   - `FriendlyIdModule` - registers custom serializers for UUID fields
   - Automatically converts UUID to FriendlyID in JSON output

3. **`friendly-id-spring-boot`**
   - Spring MVC integration
   - `FriendlyIdConfiguration` - registers Spring converters
   - Enables `@PathVariable UUID` to accept FriendlyID strings in URLs
   - Implements `WebMvcConfigurer` to add formatters

4. **`friendly-id-spring-boot-starter`**
   - Auto-configuration for Spring Boot
   - Uses `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
   - Just add to dependencies - no manual configuration needed

5. **`friendly-id-samples`**
   - Four example Spring Boot applications
   - Demonstrates different integration scenarios

### Key Design Patterns

**UUID Conversion Flow:**
```
UUID (128-bit) → BigInteger → Base62 String → FriendlyID
```

**Spring Integration Pattern:**
- Uses Spring's `Converter<S, T>` interface for bidirectional conversion
- `StringToUuidConverter`: FriendlyID string → UUID (for @PathVariable)
- `UuidToStringConverter`: UUID → FriendlyID string (for JSON serialization)
- Jackson module handles JSON serialization separately

**Auto-configuration:**
- Spring Boot 3 uses `AutoConfiguration.imports` instead of `spring.factories`
- Conditional on class presence: `@ConditionalOnExpression` with property flag
- Default enabled, can be disabled with: `com.devskiller.friendly_id.auto=false`

### Important Implementation Details

**Base62 Encoding:**
- Character set: `[0-9A-Za-z]` (62 characters)
- Leading zeros are ignored during decoding
- Maximum 22 characters for full 128-bit UUID
- Shorter UUIDs produce shorter FriendlyIDs

**Spring Boot 3 Requirements:**
- Compiler must use `-parameters` flag for parameter name reflection
- All sample projects have `<parameters>true</parameters>` in maven-compiler-plugin
- Without this flag, `@PathVariable` will fail with parameter name error

**Testing Strategy:**
- Property-based testing replaced with `@RepeatedTest(1000)`
- Random data generation using `BigInteger(128, new Random())`
- Reversibility tests: encode→decode→encode should equal original

## Common Development Scenarios

### Adding New Integration

When adding support for a new framework (e.g., Micronaut, Quarkus):

1. Create new module: `friendly-id-{framework}-integration`
2. Add converter/formatter registration for the framework
3. Register Jackson module if framework uses Jackson
4. Create sample project in `friendly-id-samples/`

### Modifying Base62 Algorithm

Core conversion logic is in `friendly-id/src/main/java/com/devskiller/friendly_id/Base62.java`:
- `encode(BigInteger)` - converts number to Base62 string
- `decode(String)` - converts Base62 string back to number
- Validate changes don't break 128-bit limit
- Ensure reversibility: all existing tests must pass

### Testing Spring Boot Integration

Sample projects demonstrate real-world usage:
- `friendly-id-spring-boot-simple` - basic REST controller
- `friendly-id-spring-boot-customized` - custom configuration
- `friendly-id-spring-boot-hateos` - HATEOAS integration
- `friendly-id-contracts` - Spring Cloud Contract testing

## Project-Specific Conventions

- Use conventional commits format (https://www.conventionalcommits.org/)
- All code comments must be in English
- Tests use JUnit 5 (not JUnit 4) - no `@RunWith`, use plain `@Test`
- Prefer `@RepeatedTest` over property-based testing libraries
- Maven compiler version: 3.13.0+ (for Java 21 support)

## Migration Notes (Current State)

This project has recently been upgraded:
- Java 8 → 21
- Spring Boot 2.2.2 → 3.4.1
- JUnit 4 → JUnit 5
- Vavr property testing → JUnit 5 `@RepeatedTest`
- GitHub Actions workflow still references Java 1.8 (needs update)

## Known Issues

- FIXME in `FriendlyIdConfiguration`: `StringToUuidConverter` should be public for better extensibility
- Sample `friendly-id-spring-boot-hateos` may need Lombok annotation processor configuration
- Travis CI badge in README is outdated (project uses GitHub Actions)
