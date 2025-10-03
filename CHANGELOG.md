# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- FriendlyId value object type (`com.devskiller.friendly_id.type.FriendlyId`) as an alternative to raw UUID
- JPA integration module (`friendly-id-jpa`) with automatic AttributeConverter
- OpenFeign integration module (`friendly-id-openfeign`) for FriendlyId support in Feign clients
- jOOQ integration module (`friendly-id-jooq`) for FriendlyId support in jOOQ
- Spring Boot JPA demo application showcasing FriendlyId with JPA, REST API, and OpenFeign

### Changed
- Upgraded from Java 8 to Java 21
- Upgraded from Spring Boot 2.2.2 to 3.4.1
- Upgraded from JUnit 4 to JUnit 5
- Migrated from Vavr property testing to JUnit 5 `@RepeatedTest`
- Updated Spring Boot auto-configuration to use `AutoConfiguration.imports` instead of `spring.factories`

### Fixed
- Fixed Jackson module serialization by adding `super.setupModule(context)` call in `FriendlyIdModule`
- Fixed Spring Cloud version compatibility (2024.0.0 for Spring Boot 3.4.1)
- Added `friendly-id-jackson-datatype` as dependency to `friendly-id-spring-boot-starter` for complete auto-configuration
- Added `friendly-id-jackson-datatype` as dependency to `friendly-id-openfeign` for JSON serialization support

### Dependencies
- `friendly-id-spring-boot-starter` now includes `friendly-id-jackson-datatype` transitively
- `friendly-id-openfeign` now includes `friendly-id-jackson-datatype` transitively

## [1.1.0] - Previous version
- Legacy implementation with UUID-only support
