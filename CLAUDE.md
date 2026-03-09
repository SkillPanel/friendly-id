# friendly-id

UUID to Base62 (FriendlyId) conversion library for Java.

## Release

Release is automated via GitHub Actions. To publish a new version:

```sh
git tag v<version>
git push origin v<version>
```

The "Release" workflow builds, deploys to Maven Central, and creates a GitHub Release.
Version is injected from the tag (`-Drevision=`), no pom.xml changes needed.

## Project structure

- `friendly-id/` — core library (FriendlyIds, FriendlyIdFormat enum)
- `friendly-id-jackson-datatype/` — Jackson 3.x (tools.jackson) module
- `friendly-id-jackson2-datatype/` — Jackson 2.x (com.fasterxml.jackson) module
- `friendly-id-jpa/` — JPA AttributeConverter
- `friendly-id-jooq/` — jOOQ Converter
- `friendly-id-openfeign/` — OpenFeign integration
- `friendly-id-spring-boot/` + `friendly-id-spring-boot-starter/` — Spring Boot auto-configuration

## Build & test

```sh
mvn test                           # all modules
mvn test -pl friendly-id-jackson-datatype  # single module
```

Java 21 required.
