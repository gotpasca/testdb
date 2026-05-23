# testdb

Simple Spring Boot microservice using an in-memory H2 database and Liquibase initialization.

## Build

On Windows (PowerShell/cmd):

```powershell
.\gradlew.bat build
.\gradlew.bat bootRun
```

On Unix/macOS:

```bash
./gradlew build
./gradlew bootRun
```

## API Endpoints

- `GET /api/options/search?name={name}&tags={tags}`
  - Returns the option metadata and all matching entries.
- `GET /api/options/{optionId}/entries`
  - Returns all entries for the option with the given UUID.

Example:

```bash
curl "http://localhost:8080/api/options/search?name=currency&tags=client"
```

## Database

- In-memory H2 database
- Initialized from Liquibase SQL changelog
- H2 Console available at `/h2-console`

## Tests

```bash
./gradlew test
```

## Notes

- Java 21 recommended.
- Liquibase runs automatically at startup to create tables and sample data.
