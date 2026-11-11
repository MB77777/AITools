# AGENTS.md

Spring Boot, Java, Java FX, Maven, PostgreSQL - check versions in pom.xml.

## Commands
- Build: `mvn clean package`
- Test: `mvnw test`
- Run: `mvn clean javafx:run`

## Folder conventions
- `src/main/java/eu/pp/aitools`
    - `api` for api endpoints
    - `services` for services
    - `controllers` for controllers
    - `model` for domain model classes
    - `dtos` for DTOs
    - `util` for util classes
- `src/test/java` mirrors the package structure.

## Spring Boot rules
- Controllers only validate input, call a service, and return a DTO.
- Services are `@Transactional` where they write; use constructor injection.
- Repositories are Spring Data JPA interfaces; do not use `EntityManager` directly in services.
- Never expose JPA entities in controller responses.
- Use `@RestControllerAdvice` for consistent error responses.
- Tests: JUnit 5; integration tests use Testcontainers PostgreSQL; unit tests use Mockito.

## Version Number Rules
- Version consists of 3 parts A.B.C
- A - main part, reserved only for big changes. 
- B - medium part of a version number - medium changes
- C - smaller part - defualt part
- You can increase A part when you are told that it is a big change
- You can increase B part when you are told that it is a medium change
- When you are not told that it's a big or medium change that increase a C part
- Increase always by 1

## Working rules
- you can work and modify files only in the current directory and all the subdirectories in the project
- you can not run any system command or tool without asking
- never run mvn clean package - i will do it myself to spare tokens
- never commit anything
- stop working and ask question if there are uncommited changes in the repository
- always increase project version in pom.xml.