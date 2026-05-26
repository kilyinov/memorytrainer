# AGENTS.md

## Cursor Cloud specific instructions

### Overview

Memory Trainer is a single-service Java/Spring Boot 3.4.4 web application (flashcard memory training) with an embedded H2 file-based database. No external services or databases are required.

### Prerequisites

- Java 17+ (Java 21 is compatible and available on the VM)
- Maven 3.8+ (installed via `sudo apt-get install -y maven`)

### Running the application

```bash
mvn spring-boot:run
```

The app starts at http://localhost:8080. The H2 database auto-creates at `./data/memorytrainer` on first run.

### Build & verify

```bash
mvn compile          # compile only
mvn verify -DskipTests  # full build including JAR packaging
```

### Lint & Tests

- No dedicated linter (e.g. Checkstyle, SpotBugs) is configured.
- No automated tests exist (`src/test/` directory is absent, no test dependencies in `pom.xml`).
- The Maven compile step serves as the primary correctness check (Java compiler warnings/errors).

### Key endpoints

See `README.md` for the full table. Most useful: `/decks`, `/decks/new`, `/decks/{id}/cards`, `/decks/{id}/study`, `/h2-console`.

### Gotchas

- The card form fields are `question` and `answer` (not `front`/`back` as one might expect from flashcard conventions).
- The H2 database file is stored at `./data/memorytrainer` relative to the working directory. This directory is git-ignored. Data persists across restarts.
- JPA `ddl-auto=update` means schema changes auto-apply on startup; no manual migrations needed.
